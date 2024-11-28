import React, { useState } from "react";
import styled from "styled-components";

const EduNav = ({ onSelect }) => {
  const [selectedButton, setSelectedButton] = useState("영상교육"); // 선택된 버튼
  const eduCategories = ["영상교육", "퀴즈"]; // 버튼 리스트

  const handleButtonClick = (buttonLabel) => {
    setSelectedButton(buttonLabel);
    onSelect(buttonLabel); // 부모 컴포넌트로 선택된 버튼 전달
  };

  return (
    <ButtonSection>
      {eduCategories.map((buttonLabel) => (
        <CustomButton
          key={buttonLabel}
          className={selectedButton === buttonLabel ? "selected" : ""}
          onClick={() => handleButtonClick(buttonLabel)} // 버튼 클릭 시 상태 업데이트
        >
          {buttonLabel}
        </CustomButton>
      ))}
    </ButtonSection>
  );
};

export default EduNav;

const ButtonSection = styled.div`
  display: flex;
  justify-content: center;
  gap: 20px;
  width: 800px;
  margin: 0 auto 30px auto;
`;

const CustomButton = styled.div`
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  width: 200px;
  height: 60px;
  border-radius: 30px;
  font-size: 24px;

  &:hover {
    color: white;
    border: 1px solid #8529fd;
    background-color: #8529fd;
  }

  &.selected {
    border: 1px solid #8529fd;
    background-color: #8529fd;
    color: #ffffff;
  }
`;
