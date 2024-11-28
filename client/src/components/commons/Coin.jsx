import React from 'react';
import styled, { keyframes } from 'styled-components';

const Coin = () => {
    return (
      <CoinWrapper>
        <Front />
        <Back />
        <FrontBack />
        <BackBack />
        <CoinBar />
        <FrontBackBefore />
        <FrontBackAfter />
        <BackBefore />
        <BackAfter />
      </CoinWrapper>
    );
  };


const CoinFace = styled.div`
  width: 100%;
  height: 100%;
  border-radius: 50%;
  position: absolute;
  top: 0;
  left: 0;
`;

const Front = styled(CoinFace)`
  background: #f7941e;
  z-index: -1;
`;

const Back = styled(CoinFace)`
  background: #f7941e;
  transform: rotateY(180deg) translateZ(10px);
  z-index: 1;
`;

const FrontBack = styled(CoinFace)`
  background: #f7941e;
  transform: translateZ(-1px);
  z-index: 2;
`;

const BackBack = styled(CoinFace)`
  background: #f7941e;
  transform: translateZ(-9px);
  z-index: -2;
`;

const CoinBar = styled.div`
  content: "";
  width: 10px;
  height: 100%;
  background: #f7941e;
  position: absolute;
  top: 0;
  left: 50%;
  z-index: 1;
  transform: rotateY(-90deg);
  transform-origin: 100% 50%;
`;

const FrontBackBefore = styled.div`
  content: "";
  width: 100%;
  height: 100%;
  border: 10px solid #fbb041;
  border-radius: 50%;
  box-sizing: border-box;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 3;
`;

const FrontBackAfter = styled.div`
  content: "$";
  padding: 22px 0 0 0;
  width: 100%;
  height: 100%;
  font-size: 4em;
  font-weight: bold;
  color: #fbb041;
  line-height: 1;
  text-align: center;
  box-sizing: border-box;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 4;
`;

const BackBefore = styled.div`
  content: "";
  width: 100%;
  height: 100%;
  border: 10px solid #fbb041;
  border-radius: 50%;
  box-sizing: border-box;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -3;
`;

const BackAfter = styled.div`
  content: "100";
  padding: 27px 0 0 0;
  width: 100%;
  height: 100%;
  font-size: 3em;
  font-weight: bold;
  color: #fbb041;
  line-height: 1;
  text-align: center;
  box-sizing: border-box;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -4;
`;


// 코인 회전 애니메이션 정의
const coinAnimation = keyframes`
  0% {
    transform: rotateY(0);
  }
  100% {
    transform: rotateY(360deg);
  }
`;

// 코인 컴포넌트 스타일 정의
const CoinWrapper = styled.div`
  margin: -50px 0 0 -50px;
  width: 100px;
  height: 100px;
  transform-style: preserve-3d;
  position: relative;
  top: 50%;
  left: 50%;
  animation: ${coinAnimation} 2s linear infinite;
`;


export default Coin;
