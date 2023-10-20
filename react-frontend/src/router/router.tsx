import { Navigate, createBrowserRouter } from "react-router-dom";
import Home from "../views/Home";
import Login from "../views/Login";

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
      element: <Login />,
    },
    {
      path: "/home",
      element: <Home />,
    },
  ]);
};

export default getRouter;
