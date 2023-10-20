import { useMsal } from "@azure/msal-react";
import { useNavigate } from "react-router-dom";
//import { useEffect } from "react";

const Login = () => {
  const { instance } = useMsal();
  const navigate = useNavigate();

  const handleLogin = async () => {
    console.log("Login");
    const loginRedirect = await instance.loginRedirect();
    console.log("loginRedirect", loginRedirect);
    navigate("/home");
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
