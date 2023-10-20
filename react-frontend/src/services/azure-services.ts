import axios from "axios";
import { graphConfig } from "../authConfig";
import { AxiosHeaders } from "axios";

export const getMsGraph = async (accessToken: string) => {
  const headers = new AxiosHeaders();
  headers.setAuthorization(`Bearer ${accessToken}`);
  return axios.get(graphConfig.graphMeEndpoint, { headers: headers });
};
