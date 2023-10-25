import { useMsal } from "@azure/msal-react";

const Login = () => {
  const { instance } = useMsal();

  const handleLogin = async () => {
    console.log("Login");
    await instance.clearCache();
    await instance.handleRedirectPromise();
    const loginRedirect = await instance.loginRedirect();
    console.log("loginRedirect", loginRedirect);
  };

  return <button onClick={handleLogin}>Login</button>;
};

export default Login;
