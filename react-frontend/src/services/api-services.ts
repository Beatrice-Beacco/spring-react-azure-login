import axios, { AxiosHeaders } from "axios";
import { BasicResponse } from "../typescript/response-types";

export const checkEndpoint = (accessToken: string) => {
  const headers = new AxiosHeaders();
  headers.setAuthorization(`Bearer ${accessToken}`);
  return axios.get<BasicResponse<string>>("http://localhost:8080/check", {
    headers: headers,
  });
};
