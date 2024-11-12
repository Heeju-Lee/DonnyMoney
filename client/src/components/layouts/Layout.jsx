import React from "react";
import styled from "styled-components";

// TODO 레이아웃 작업
export default function Layout({ children }) {
  return (
    <Section>
      <Main>{children}</Main>
    </Section>
  );
}

const Section = styled.section`
  width: 100%;

  border: 1px solid blue;
`;

const Main = styled.main`
  width: 100%;
  max-width: 1360px;
  padding: 80px 40px 0 40px;
  margin: 0 auto;
  border: 1px solid gray;
`;
