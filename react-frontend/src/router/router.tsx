import { Navigate, createBrowserRouter } from "react-router-dom";
import Home from "../views/Home";
import Login from "../views/Login";

const getRouter = (isAuthenticated: boolean) => {
  return createBrowserRouter([
    {
      path: "/",
      element: <Navigate to={isAuthenticated ? "/home" : "/login"} />,
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
