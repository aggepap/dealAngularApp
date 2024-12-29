export interface UserInsertDTO {
  username: string;
  password: string;
  role?: string;
}
export interface UserReadOnlyDTO {
  uuid: string;
  username: string;
  role: string;
}

export interface UserRegisterFormData {
  username: string;
  password: string;
  confirmPassword: string;
}

export interface Creds {
  email: string;
  password: string;
}

export interface LoggedInUser {
  sub: string;
  role: string;
}

export interface AuthResponse {
  username: string;
  token: string;
}
