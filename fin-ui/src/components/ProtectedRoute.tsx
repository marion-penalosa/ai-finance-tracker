import type { JSX } from "react";
import { Navigate } from "react-router-dom";

interface ProtectedRouteProps {
  children: JSX.Element;
}

const ProtectedRouteProps = ({children}: ProtectedRouteProps) =>   {
    const isAuthenticated = Boolean(localStorage.getItem("token"));

    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    return children;
};

export default ProtectedRouteProps;