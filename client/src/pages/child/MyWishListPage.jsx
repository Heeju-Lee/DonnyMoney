import React, { useRef, useState } from "react";
import styled from "styled-components";
import { FinishWishList } from "../../components/pages/child/register/FinishWishList";
import Activewish from "../../components/pages/child/wish/ActiveWish";

const MyWishListPage = () => {
  return (
    <>
      <Title>나의 꿈창고</Title>
      <Activewish />

      {/* 성공한 위시 목록 */}
      <FinishWishList />
    </>
  );
};
const Title = styled.h3`
  color: black;
  text-align: center;
  padding-top: 50px;
  font-weight: bold;
`;
export default MyWishListPage;
