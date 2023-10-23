import axios, { AxiosHeaders } from "axios";

export const checkEndpoint = (accessToken: string) => {
  const headers = new AxiosHeaders();
  headers.setAuthorization(`Bearer ${accessToken}`);
  return axios.get("http://localhost:8080/check", { headers: headers });
};
