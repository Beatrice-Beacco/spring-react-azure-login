import axios, { AxiosHeaders } from "axios";

export const checkEndpoint = (accessToken: string) => {
  const headers = new AxiosHeaders();
  headers.setAuthorizationBearer(accessToken);
  return axios.get("localhost:8080/check", { headers: headers });
};
