import React, { useContext, useEffect, useRef, useState } from "react";
import styled from "styled-components";
import { Link, useNavigate } from "react-router-dom";

import { Button } from "react-bootstrap";
import MoneyPlanPage from "../../pages/child/MoneyPlanPage";

import { LogingedContext, AuthContext } from "../../App";
import { ParentNotificationIcon } from "../pages/parent/ParentNotificationIcon";
import { ChildNotificationIcon } from "../pages/child/ChildNotificationIcon";

const Header = () => {
  const navigate = useNavigate();
  let logingedCon = useContext(LogingedContext);
  const logoutRef = useRef(null); // 로그아웃 DOM 참조
  const [logoutOpen, setLogoutOpen] = useState(false); // 로그아웃 버튼

  // 로그인 유저정보
  const { memberNo, role, name, authorization } = useContext(AuthContext);

  // const role = localStorage.getItem("role");

  const logoutCheck = () => {
    setLogoutOpen(false);
    localStorage.removeItem("memberNo");
    localStorage.removeItem("id");
    localStorage.removeItem("name");
    localStorage.removeItem("Authorization");
    localStorage.removeItem("role");

    logingedCon.onLoggedChange(false);

    navigate("/");
  };

  // 로그아웃 버튼 바깥 클릭 감지시 처리
  useEffect(() => {
    const handleClickOutside = (event) => {
      // 로그아웃 메뉴 외부를 클릭하면 닫기
      if (logoutRef.current && !logoutRef.current.contains(event.target)) {
        setLogoutOpen(false);
      }
    };
    // 전역 클릭 이벤트 등록
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      // 컴포넌트 언마운트 시 이벤트 제거
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <HeaderSection>
      <Container>
        {/* 로고 */}
        <a href="/">
          <LeftSection>
            <LogoOuter>
              <img
                src="/images/donnymoney-logo.png"
                alt="logo"
                width={"100%"}
                height={"auto"}
              />
            </LogoOuter>
            <p>도니머니</p>
          </LeftSection>
        </a>

        {/* 메뉴 */}
        {logingedCon.isLoggedIn &&
          (role === "ROLE_PARENT" ? (
            <MenuSection>
              <Link to="/monthly-report">
                <span>월간리포트</span>
              </Link>
              <Link to="/agreement">
                <span>용돈계약서</span>
              </Link>
              <Link to="/wish-list">
                <span>위시리스트</span>
              </Link>
            </MenuSection>
          ) : (
            <MenuSection>
              <Link to="/money-plan">
                <span>소비 계획 세우기</span>
              </Link>
              <Link to="/edu">
                <span>교육</span>
              </Link>
              <Link to="/mywish-list">
                <span>위시 리스트</span>
              </Link>
              <Link to="/child-report">
                <span>소비 리포트 보기</span>
              </Link>
            </MenuSection>
          ))}

        {/* 로그인, 로그아웃, 유저네임, 알림 영역 */}

        <RightSection>
          {!logingedCon.isLoggedIn && (
            <>
              <Link to="/login">
                <span>로그인</span>
              </Link>
              <Link
                to="/register"
                onClick={(e) => {
                  e.preventDefault(); // 기본 동작 막음
                  window.location.href = "/register"; // 강제로 새로고침하며 이동
                }}
              >
                <span>회원가입</span>
              </Link>
            </>
          )}

          {/* 유저 로그인시 보이는 정보 */}
          {logingedCon.isLoggedIn && (
            <UserInfo>
              {role === "ROLE_PARENT" && <ParentNotificationIcon />}
              {role === "ROLE_CHILD" && <ChildNotificationIcon />}

              <div
                className="user_name"
                onClick={() =>
                  logoutOpen ? setLogoutOpen(false) : setLogoutOpen(true)
                }
              >
                {name}
                <span className="role_keyword">
                  {role === "ROLE_PARENT" ? "학부모님" : "학생"}
                </span>
              </div>

              {/* 로그아웃 버튼  */}
              {logoutOpen && (
                <LogoutButton
                  className="logout_keyword"
                  ref={logoutRef}
                  onClick={logoutCheck}
                >
                  로그아웃
                </LogoutButton>
              )}
            </UserInfo>
          )}
        </RightSection>
      </Container>
    </HeaderSection>
  );
};

const HeaderSection = styled.div`
  position: sticky;
  top: 0;
  z-index: 1000;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

  width: 100%;
  height: 80px;
  display: flex;
  align-items: center;
`;
const Container = styled.div`
  width: 100%;
  max-width: 1360px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
  margin: 0 auto;
  font-weight: bold;
`;

// 왼쪽 영역 로고
const LeftSection = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 24px;

  p {
    margin: 0;
  }
`;
const LogoOuter = styled.div`
  width: 50px;
  height: 50px;
  position: relative;
`;

// 가운데 메뉴 영역
const MenuSection = styled.div`
  display: flex;
  padding-left: 0;
  margin-bottom: 0;
  gap: 100px;
  font-size: 20px;

  /* border: 1px solid red; */
`;

// 오른쪽 영역
const RightSection = styled.div`
  display: flex;
  align-items: center;
  gap: 15px;
  font-size: 20px;

  .user_name {
    cursor: pointer;
    margin-left: 12px;
  }
  .role_keyword {
    margin-left: 8px;
    color: #fd9827;
  }
  /* border: 1px solid red; */
`;
const UserInfo = styled.div`
  position: relative;
  display: flex;
  align-items: center;
`;

const LogoutButton = styled.div`
  cursor: pointer;
  position: absolute;
  top: 40px;
  right: 0;
  font-size: 16px;
  border: 2px solid lightgray;
  padding: 10px 15px;
  border-radius: 10px;
  background-color: white;
  &:hover {
    background-color: #9774fb;
    color: #ffffff;
    border: 2px solid #9774fb;
  }
`;

export default Header;
