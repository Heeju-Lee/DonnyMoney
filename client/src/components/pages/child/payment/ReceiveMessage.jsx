import React, { useState, useEffect } from "react";
import axios from "axios";
import styled from "styled-components";
const ReceiveMessage = () => {
  const token = localStorage.getItem("Authorization");
  const childNum = localStorage.getItem("memberNo");
  const [notificationData, setNotificationData] = useState(null);
  const [isMessageVisible, setIsMessageVisible] = useState(false);
  useEffect(() => {
    axios({
      method: "GET",
      url: `${process.env.REACT_APP_BASE_URL}/notification/child/${childNum}`,
      headers: {
        Authorization: `${token}`,
      },
    })
      .then((res) => {
        const data = res.data;
        const firstFeedbackMessage = data.find(
          (item) => item.category === "parentMsg"
        );
        if (firstFeedbackMessage) {
          setNotificationData(firstFeedbackMessage);
        } else {
          // alert("피드백 메시지가 없습니다.");
        }
      })
      .catch((err) => {
        alert("메세지 불러오기 실패");
        console.error(err);
      });
  }, [childNum]);
  const showMessage = () => {
    setIsMessageVisible(true);
  };
  return (
    <ContainAll>
      <ContainContent>
        {notificationData === null ? (
          <Title>
            <p>:이메일2:도착한 메세지가 없습니다.:이메일2:</p>
          </Title>
        ) : (
          <>
            <Title>:이메일2:메세지가 도착했습니다:이메일2:</Title>
            <MessageWrapper>
              {!isMessageVisible ? (
                <MessagePrompt>
                  <p>버튼을 눌러 메시지를 확인하세요.</p>
                  <ConfirmButton onClick={showMessage}>확인하기</ConfirmButton>
                </MessagePrompt>
              ) : (
                <LetterBox>
                  <p>:스크롤: To. 사랑스러운 우리 아이에게</p>
                  <Message>{notificationData.message}</Message>
                  <p>
                    :펜촉이_왼쪽_아래를_향한_만년필: From. 너를 사랑하는
                    부모님이
                  </p>
                </LetterBox>
              )}
            </MessageWrapper>
          </>
        )}
      </ContainContent>
    </ContainAll>
  );
};
const ContainAll = styled.div`
  margin: 0 auto;
  width: 100%;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
  border-radius: 15px;
  margin: 20px 0px;
  background-color: hsl(0, 0%, 100%);
`;
const ContainContent = styled.div`
  padding: 30px;
  border-radius: 15px;
  margin: 0px 20px;
`;
const Title = styled.div`
  font-size: 30px;
  margin: 0 auto;
  margin: 20px 0px;
  text-align: center;
  /* color: #8529FD; */
`;
const ConfirmButton = styled.button`
  width: fit-content;
  padding: 10px;
  border-radius: 15px;
  background-color: #8529fd;
  color: white;
  font-size: 15px;
  display: block;
  margin: 20px auto; /* 버튼을 중앙에 배치 */
  transition: all 0.3s ease;
  &:hover {
    background-color: #4b0e93;
  }
`;
const MessageWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const MessagePrompt = styled.div`
  text-align: center;
`;
const LetterBox = styled.div`
  background-color: #fdf9f1;
  padding: 30px;
  border-radius: 20px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  width: 90%;
  max-width: 80%;
  margin-top: 20px;
  text-align: center;
  p {
    margin: 15px 0;
    font-size: 18px;
    line-height: 1.6;
  }
`;
const Message = styled.p`
  font-size: 20px;
  font-weight: 400;
  margin: 20px 0;
  line-height: 1.8;
  white-space: pre-wrap;
  color: #5e5e5e;
`;
export default ReceiveMessage;
