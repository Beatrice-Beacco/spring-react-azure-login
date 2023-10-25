import { useIsAuthenticated } from "@azure/msal-react";
import { PropsWithChildren } from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }: PropsWithChildren) => {
  const isAuthenticated = useIsAuthenticated();

  if (!isAuthenticated) return <Navigate replace to="/login" />;

  return <>{children}</>;
};

export default ProtectedRoute;
