package com.fins.user_service.auth;

import com.fins.user_service.account.dtos.AccountReq;
import com.fins.user_service.account.dtos.AccountRes;
import com.fins.user_service.account.model.Account;
import com.fins.user_service.account.model.Role;
import com.fins.user_service.account.repository.AccountRepository;
import com.fins.user_service.account.service.AccountService;
import com.fins.user_service.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) throws InvalidCredentialsException {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            Account account = accountRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid Email: " + loginRequest.getEmail()));

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw new InvalidCredentialsException("Invalid Credentials");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<AccountRes> registerAccount(@Valid @RequestBody AccountReq request){
        AccountRes response = accountService.registerAccount(request);
        response.setRole(Role.USER);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
