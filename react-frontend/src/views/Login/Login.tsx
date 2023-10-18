import { useMsal } from "@azure/msal-react";
//import { useEffect } from "react";

const Login = () => {
  const { instance } = useMsal();

  const handleLogin = async () => {
    console.log("Login");
    await instance.clearCache();
    await instance.handleRedirectPromise();
    await instance.loginRedirect();
  };

  /*   useEffect(() => {
    handleLogin();
  }, []); */

  return (
    <div>
      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default Login;
