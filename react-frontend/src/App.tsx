import { useIsAuthenticated } from "@azure/msal-react";
import { RouterProvider } from "react-router-dom";
import getRouter from "./router/router";

function App() {
  const isAuthenticated = useIsAuthenticated();

  return <RouterProvider router={getRouter(isAuthenticated)} />;
}

export default App;
