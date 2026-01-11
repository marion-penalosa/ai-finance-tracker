// src/api/auth.api.ts
import { apiClient } from "./client";

interface LoginPayload {
    email: string;
    password: string;
}

interface LoginResponse {
    message: string;
    token: string;
}

export const login = async (payload: LoginPayload): Promise<LoginResponse> => {
    return await apiClient("/auth/login", {
        method: "POST",
        body: payload,
    });
};

export const logout = () => {
    localStorage.removeItem("token");
};
