import React from "react";
import { createGlobalStyle } from "styled-components";

// 전역 스타일 정의
export const GlobalStyle = createGlobalStyle`
  body {
    margin: 0;
    padding: 0;
    font-family: 'Arial', sans-serif;
    background-color: #F3EDFD;
    
  }

  a {
    text-decoration: none;
    color: inherit;
  }
  ul {
    list-style: none;
  }

  * {
    box-sizing: border-box;
  }

`;
