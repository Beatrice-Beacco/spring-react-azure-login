import { useMsal } from "@azure/msal-react";
import React, { useEffect, useState } from "react";
import { loginRequest, tokenRequest } from "../../authConfig";
import { getMsGraph } from "../../services/azure-services";
import { checkEndpoint } from "../../services/api-services";
import axios, { AxiosError } from "axios";

const Home = () => {
  const { instance, accounts } = useMsal();
  const userAccount = accounts[0];
  //TODO: da sostituire con tipaggio vero
  const [graphData, setGraphData] = useState<{
    givenName: string;
    surname: string;
    userPrincipalName: string;
    id: number;
  }>();
  const [springMessage, setSpringMessage] = useState<string>();

  useEffect(() => {
    getGraphData();
  }, [userAccount]);

  //Specific for Graph API
  const getGraphData = async () => {
    console.log("user account", userAccount);

    //TODO: save in storage and handle refresh
    //TODO: uso sempre lo stesso metodo per refreshare il token
    const loginTokenResponse = await instance.acquireTokenSilent({
      ...loginRequest,
      account: userAccount,
    });

    const graphResponse = await getMsGraph(loginTokenResponse.accessToken);
    setGraphData(graphResponse.data);
  };

  //Specific for Azure AD App
  const checkTokenValidity = async () => {
    const tokenResponse = await instance.acquireTokenSilent({
      ...tokenRequest,
      account: userAccount,
    });

    try {
      const checkResponse = await checkEndpoint(tokenResponse.accessToken);
      setSpringMessage(checkResponse.data);
      //@
    } catch (e) {
      const error = e as AxiosError;
      if (axios.isAxiosError(error)) {
        setSpringMessage(error?.response?.data as string);
      }
      console.log("error", error);
    }
  };

  const handleLogout = async () => {
    console.log("Logout");
    await instance.clearCache();
    await instance.handleRedirectPromise();
    await instance.logoutRedirect({
      postLogoutRedirectUri: "/",
    });
  };

  if (!graphData) return <div>Loading...</div>;

  return (
    <div>
      <button onClick={handleLogout}>Logout</button>
      <h1>Account data (Microsoft Graph API)</h1>
      <p>
        <strong>First Name: </strong> {graphData.givenName}
      </p>
      <p>
        <strong>Last Name: </strong> {graphData.surname}
      </p>
      <p>
        <strong>Email: </strong> {graphData.userPrincipalName}
      </p>
      <p>
        <strong>Id: </strong> {graphData.id}
      </p>
      <button onClick={checkTokenValidity}>
        Check token validity (Spring Backend)
      </button>
      {springMessage && <p>Spring backend message: {springMessage}</p>}
    </div>
  );
};

export default Home;
