import React from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

const AccessDeniedPage = () => {
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate(-1); // 이전 페이지로 이동
  };

  const handleGoHome = () => {
    navigate('/'); // 홈으로 이동
  };

  return (
    <Container>
      <ErrorMessage>접근할 수 없는 페이지입니다.</ErrorMessage>
      <ButtonGroup>
        <Button onClick={handleGoBack}>이전 페이지로 돌아가기</Button>
        <Button onClick={handleGoHome}>홈으로 이동</Button>
      </ButtonGroup>
    </Container>
  );
};

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f8f9fa;
`;

const ErrorMessage = styled.h1`
  color: crimson;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
`;

const ButtonGroup = styled.div`
  display: flex;
  gap: 10px;
`;

const Button = styled.button`
  padding: 10px 20px;
  background-color: #8529fd;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;

  &:hover {
    background-color: #8529fd;
  }
`;

export default AccessDeniedPage;
