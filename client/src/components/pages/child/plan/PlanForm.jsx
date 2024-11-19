import React, { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { Form, Button, FormGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { PlanContext } from "../../../../pages/context/MoneyPlanContext";
import { AuthContext } from "../../../../App";

const Container = styled.div`
  background-color: #886eff;
  min-height: 80vh;
  width: 50vh;
  border-radius: 30px;
  margin: -10px 0 50px 40px;
  border: 5px solid #c8bef3;
`;

const Title = styled.h3`
  color: white;
  text-align: center;
  padding-top: 50px;
  font-weight: bold;
`;

const FormPlan = styled.form`
  width: 33vh;
  margin: 0 auto;
  padding: 5px 0 0 20px;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
`;

const FormBox = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 3vh 0 -5vh;
`;

const FormTitle = styled.h4`
  text-align: center;
  font-size: 1rem;
  font-weight: bold;
  color: #ffffff;
  margin: 0 0 0 1vh;
`;

const FormImg = styled.img`
  display: flex;
  align-self: baseline;
  width: 50px;
  height: 50px;
  margin: 0 0 0 15px;
`;

const FormInput = styled.input`
  padding: 8px;
  border: 5px solid #c8bef3;
  border-radius: 10px;
  outline: none;
  width: 100%;
`;

const UpdateBtn = styled.button`
  background-color: #4829d7;
  color: white;
  font-style: bold;
  font-size: 1.2rem;
  border: 2px solid #4829d7;
  border-radius: 10px;
  width: 15vw;
  height: 5.8vh;
  margin-top: 1vh;
  font-weight: bold;
`;
const FormG = styled.div`
  display: grid;
  grid-template-columns: 1fr auto; /* 첫 번째 칸은 콘텐츠 크기, 두 번째 칸은 남은 공간 차지 */
  align-items: center;
  padding: 10px;
`;
const PlanForm = () => {
  const { plan, setPlan } = useContext(PlanContext);
  const { authorization } = useContext(AuthContext); // AuthContext에서 토큰 가져오기
  const [formData, setFormData] = useState({
    food: plan.food,
    cvs: plan.cvs,
    shopping: plan.shopping,
    transport: plan.transport,
    saving: plan.saving,
    others: plan.others,
  });

  const token = authorization;
  const today = new Date();
  const currentYear = today.getFullYear();
  const currentMonth = today.getMonth() + 1;

  // 초기값을 API에서 가져오기
  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const response = await axios({
          method: "GET",
          url: `/children/show/plans?year=${currentYear}&month=${currentMonth}`,
          headers: {
            Authorization: token,
            "Content-Type": "application/json",
          },
        });
        console.log("Initial API Response:", response.data);

        // 가져온 데이터를 PlanContext와 formData에 설정
        const planData = response.data;
        console.log("초기값으로 가져온 planData", planData);
        setPlan(planData);
        setFormData({
          food: planData.food || 0,
          cvs: planData.cvs || 0,
          shopping: planData.shopping || 0,
          transport: planData.transport || 0,
          saving: planData.saving || 0,
          others: planData.others || 0,
        });
      } catch (error) {
        console.error(
          "Error fetching initial data:",
          error.response || error.message
        );
      }
    };

    fetchInitialData();
  }, [currentYear, currentMonth, setPlan, token]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    const numericValue = value.replace(/[^0-9]/g, ""); // 숫자만 추출
    setFormData((prevData) => ({
      ...prevData,
      [name]: numericValue,
    }));
  };

  const handleUpdatePlan = () => {
    console.log("Updating Plan:", formData);
    setPlan(formData);
    alert("상태가 업데이트되었습니다.");
  };

  return (
    <Container>
      <Title>카테고리</Title>
      <FormPlan>
        <FormG>
          <FormBox>
            <FormImg
              src={`${process.env.PUBLIC_URL}/icons/cvs.png`}
              alt="cvs"
            />
            <FormTitle>편의점</FormTitle>
          </FormBox>
          <FormInput
            type="text"
            name="cvs"
            value={Number(formData.cvs).toLocaleString("ko-KR")}
            onChange={handleInputChange}
          />
        </FormG>
        <FormG>
          <FormBox>
            <FormImg
              src={`${process.env.PUBLIC_URL}/icons/food.png`}
              alt="food"
            />
            <FormTitle>음식</FormTitle>
          </FormBox>
          <FormInput
            type="text"
            name="food"
            value={Number(formData.food).toLocaleString("ko-KR")}
            onChange={handleInputChange}
          />
        </FormG>
        <FormG>
          <FormBox>
            <FormImg
              src={`${process.env.PUBLIC_URL}/icons/shopping.png`}
              alt="shopping cart"
            />
            <FormTitle>쇼핑</FormTitle>
          </FormBox>
          <FormInput
            type="text"
            name="shopping"
            value={Number(formData.shopping).toLocaleString("ko-KR")}
            onChange={handleInputChange}
          />
        </FormG>

        <FormG>
          <FormBox>
            <FormImg
              src={`${process.env.PUBLIC_URL}/icons/transport.png`}
              alt="transport"
            />
            <FormTitle>교통</FormTitle>
          </FormBox>
          <FormInput
            type="text"
            name="transport"
            value={Number(formData.transport).toLocaleString("ko-KR")}
            onChange={handleInputChange}
          />
        </FormG>

        <FormG>
          <FormBox>
            <FormImg
              src={`${process.env.PUBLIC_URL}/icons/saving.png`}
              alt="saving"
            />
            <FormTitle>저축</FormTitle>
          </FormBox>
          <FormInput
            type="text"
            name="saving"
            value={Number(formData.saving).toLocaleString("ko-KR")}
            onChange={handleInputChange}
          />
        </FormG>

        <FormG>
          <FormBox>
            <FormImg
              src={`${process.env.PUBLIC_URL}/icons/others.png`}
              alt="others"
            />
            <FormTitle>기타</FormTitle>
          </FormBox>
          <FormInput
            type="text"
            name="others"
            value={Number(formData.others).toLocaleString("ko-KR")}
            onChange={handleInputChange}
          />
        </FormG>

        <UpdateBtn type="button" onClick={handleUpdatePlan}>
          미리보기
        </UpdateBtn>
      </FormPlan>
    </Container>
  );
};

export default PlanForm;
