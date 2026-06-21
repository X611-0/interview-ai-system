import http from './http';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email?: string;
}

export const login = (data: LoginRequest) => {
  return http.post<LoginResponse>('/auth/login', data);
};

export const register = (data: RegisterRequest) => {
  return http.post('/auth/register', data);
};

