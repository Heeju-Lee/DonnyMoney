// import React, { createContext, useState, useEffect, useContext } from "react";

// // 로그인 상태 관리
// // AuthContext 생성
// const AuthContext = createContext();

// export const AuthProvider = ({ children }) => {
//   // 초기 상태를 localStorage에서 가져오기
//   const [authState, setAuthState] = useState(() => {
//     const savedState = localStorage.getItem("authState");
//     return savedState
//       ? JSON.parse(savedState) // 저장된 상태 복원
//       : { isLoggedIn: false, user: null }; // 기본 상태
//   });

//   // 상태 변경 시 localStorage에 저장
//   useEffect(() => {
//     localStorage.setItem("authState", JSON.stringify(authState));
//   }, [authState]);

//   const login = (user) => {
//     setAuthState({ isLoggedIn: true, user });
//   };

//   const logout = () => {
//     setAuthState({ isLoggedIn: false, user: null });
//     localStorage.removeItem("authState"); // 로그아웃 시 localStorage 정리
//   };

//   return (
//     <AuthContext.Provider value={{ authState, login, logout }}>
//       {children}
//     </AuthContext.Provider>
//   );
// };

// // Context 소비를 위한 Custom Hook
// export const useAuth = () => {
//   return useContext(AuthContext);
// };
