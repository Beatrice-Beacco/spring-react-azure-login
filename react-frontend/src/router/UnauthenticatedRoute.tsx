import { useIsAuthenticated } from "@azure/msal-react";
import React, { PropsWithChildren } from "react";
import { Navigate } from "react-router-dom";

const UnauthenticatedRoute = ({ children }: PropsWithChildren) => {
  const isAuthenticated = useIsAuthenticated();

  if (isAuthenticated) return <Navigate replace to="/home" />;

  return <>{children}</>;
};

export default UnauthenticatedRoute;
