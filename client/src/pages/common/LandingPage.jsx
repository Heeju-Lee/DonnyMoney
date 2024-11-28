import React, { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import { Landing1 } from "../../components/pages/landing/Landing1";
import { Landing2 } from "../../components/pages/landing/Landing2";
import { Landing3 } from "../../components/pages/landing/Landing3";
import { Landing4 } from "../../components/pages/landing/Landing4";

const LandingPage = () => {
  const sectionsRef = useRef([]); // 섹션 DOM 요소를 참조하기 위한 배열

  const landingComponents = [Landing1, Landing2, Landing3, Landing4]; // 랜딩 컴포넌트를 배열로 정의
  // const landingComponents = [Landing3, Landing4]; // 랜딩 컴포넌트를 배열로 정의

  // fade-in 효과를 위한 IntersectionObserver
  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            const fadeElements = entry.target.querySelectorAll(".fade-in");
            fadeElements.forEach((el) => el.classList.add("visible"));
          }
        });
      },
      { threshold: 0.8 } // 80%가 화면에 보일 때 트리거
    );

    // 각 섹션에 대해 observer 등록
    sectionsRef.current.forEach((section) => {
      if (section) {
        observer.observe(section);
      }
    });

    return () => {
      // 클린업 시 observer 해제
      sectionsRef.current.forEach((section) => {
        if (section) {
          observer.unobserve(section);
        }
      });
    };
  }, []);

  return (
    <Outer>
      {landingComponents.map((Component, index) => (
        <Section
          key={index}
          ref={(el) => (sectionsRef.current[index] = el)} // 각 섹션에 ref 할당
        >
          <Component /> {/* 동적으로 컴포넌트를 렌더링 */}
        </Section>
      ))}
    </Outer>
  );
};

const Outer = styled.div`
  scroll-snap-type: y mandatory; /* 세로 스냅 활성화 */
  overflow-y: scroll;
  height: calc(100vh - 80px);
`;
const Section = styled.div`
  height: calc(100vh - 80px);
  display: flex;
  justify-content: center;
  align-items: center;
  scroll-snap-align: start; /* 각 섹션 시작 위치에서 스냅 */
`;
export default LandingPage;
