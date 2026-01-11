import { useNavigate } from "react-router-dom";
import { logout } from "../../api/auth.api";

const LogoutButton = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div>
      <button
        onClick={handleLogout}
        className="p-2 bg-primary text-white hover:bg-cyanDark rounded"
      >
        Logout
      </button>
    </div>
  );
};

export default LogoutButton;
