import axios, { AxiosHeaders } from "axios";

export const checkEndpoint = (accessToken: string) => {
  const bearerToken = `Bearer ${accessToken}`;
  axios.defaults.headers.common["Authorization"] = bearerToken;
  /*   const headers = new AxiosHeaders();
  headers.setAuthorization(`Bearer ${accessToken}`); */
  return axios.get("http://localhost:8080/check");
};
