import React from "react";
import styled from "styled-components";

// 레이아웃
export default function Layout({ children, scrollEnabled = true }) {
  return (
    <Section scrollEnabled={scrollEnabled}>
      <Main>{children}</Main>
    </Section>
  );
}

// Section 스타일 (scrollEnabled prop 활용)
const Section = styled.section.withConfig({
  shouldForwardProp: (prop) => prop !== "scrollEnabled", //scrollEnabled를 DOM에 전달하지 않음
})`
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  /* 백그라운드 이미지 추가 */
  /* background-image: url("images/vecteezy_purple-watercolor-abstract-texture-rectangle-background_10753918.JPG"); */
  /* background-image: url('images/vecteezy_greenboard-background-chalkboard-with-wooden-frame-in-room_15746041.jpg'); */
  /* background-image: url('images/vecteezy_blank-notebook-a-pencil-and-space-are-on-top-of-a-yellow_5194078.jpg'); */
  /* background-image: url('images/beige-paper-texture-background_11041609.jpg'); */
  /* background-image: url('images/purple-brick-cement-wall-in-home-broken-and-old-surface-for_37145567.jpg'); */
  /* background-image: url('images/paper-texture-or-background_2386343.jpg'); */
  /* background-image: url('images/paper-texture-background_11041609.jpg'); */

  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;

  overflow-y: ${(props) => (props.scrollEnabled ? "auto" : "hidden")};
`;

const Main = styled.main`
  width: 100%;
  max-width: 1360px;

  padding: 80px 40px 0 40px; // 수정예정
  margin: 0 auto;
  flex-grow: 1;

  /* border: 1px solid gray; */
`;
