import { useIsAuthenticated } from "@azure/msal-react";
import { RouterProvider } from "react-router-dom";
import getRouter from "./router/router";
import { useEffect, useState } from "react";

function App() {
  const isAuthenticated = useIsAuthenticated();
  const [router, setRouter] = useState(getRouter(isAuthenticated));

  useEffect(() => {
    setRouter(getRouter(isAuthenticated));
  }, [isAuthenticated]);

  console.log("isAuthenticated", isAuthenticated);

  return <RouterProvider router={router} />;
}

export default App;
