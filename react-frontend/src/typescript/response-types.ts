export interface BasicResponse<T> {
  statusCode: number;
  message: string;
  success: boolean;
  data: T;
}
