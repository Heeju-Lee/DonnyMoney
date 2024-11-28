import React, { createContext, useContext } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ user }) => {
  const userInfo = {
    memberNo: localStorage.getItem("memberNo"),
    role: localStorage.getItem("role"),
    name: localStorage.getItem("name"),
    authorization: localStorage.getItem("Authorization"),
  };

  return <AuthContext.Provider value={userInfo}>{user}</AuthContext.Provider>;
};
export const useAuth = () => {
  return useContext(AuthContext);
};
