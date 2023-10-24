import { useIsAuthenticated, useMsal } from "@azure/msal-react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
//import { useEffect } from "react";

const Login = () => {
  const isAuthenticated = useIsAuthenticated();
  const { instance } = useMsal();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/home");
    }
  }, [isAuthenticated, navigate]);

  const handleLogin = async () => {
    if (isAuthenticated) {
      goToHome();
      return;
    }
    console.log("Login");
    await instance.clearCache();
    await instance.handleRedirectPromise();
    const loginRedirect = await instance.loginRedirect();
    console.log("loginRedirect", loginRedirect);
  };

  const goToHome = () => {
    navigate("/home");
  };

  return <button onClick={handleLogin}>Login</button>;
};

export default Login;
