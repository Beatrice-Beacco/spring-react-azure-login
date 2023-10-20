import { useMsal } from "@azure/msal-react";
import React, { useEffect, useState } from "react";
import { loginRequest } from "../../authConfig";
import { getMsGraph } from "../../services/azure-services";
import { checkEndpoint } from "../../services/api-services";

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

  useEffect(() => {
    getGraphData();
  }, [userAccount]);

  const getGraphData = async () => {
    console.log("user account", userAccount);

    //TODO: save in storage and handle refresh
    //TODO: uso sempre lo stesso metodo per refreshare il token
    const tokenResponse = await instance.acquireTokenSilent({
      ...loginRequest,
      account: userAccount,
    });

    const graphResponse = await getMsGraph(tokenResponse.accessToken);
    setGraphData(graphResponse.data);

    const checkResponse = await checkEndpoint(tokenResponse.accessToken);
    console.log("checkResponse", checkResponse);
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
    </div>
  );
};

export default Home;
