import { Navigate, createBrowserRouter } from "react-router-dom";
import Home from "../views/Home";
import Login from "../views/Login";
import ProtectedRoute from "./ProtectedRoute";
import UnauthenticatedRoute from "./UnauthenticatedRoute";

const getRouter = (isAuthenticated: boolean) => {
  return createBrowserRouter([
    {
      path: "/",
      element: isAuthenticated ? (
        <Navigate replace to="/home" />
      ) : (
        <Navigate replace to="/login" />
      ),
    },
    {
      path: "/login",
      element: (
        <UnauthenticatedRoute>
          <Login />
        </UnauthenticatedRoute>
      ),
    },
    {
      path: "/home",
      element: (
        <ProtectedRoute>
          <Home />
        </ProtectedRoute>
      ),
    },
  ]);
};

export default getRouter;
