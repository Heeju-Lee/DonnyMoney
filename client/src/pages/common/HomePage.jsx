import React from "react";
import styled from "styled-components";

const HomePage = () => {
  return (
    <Outer>
      <h1>랜딩페이지</h1>
    </Outer>
  );
};

const Outer = styled.div`
  width: 100%;
  height: 100vh;
`;

export default HomePage;
