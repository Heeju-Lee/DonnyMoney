import React, { useRef, useState } from "react";
import styled from "styled-components";
import Slider from "react-slick"; // react-slick 라이브러리 임포트
import "slick-carousel/slick/slick.css"; // slick 스타일시트 임포트
import "slick-carousel/slick/slick-theme.css";
import { WishItemCard } from "../../../commons/WishItemCard";

import { Modal } from "../../../commons/Modal";
import WishDetailBox from "./WishDetailBox";
const Activewish = (imgSrc) => {
  const [isModalOpen, setModalOpen] = useState(false); // 모달 열리고 닫고 상태 보관
  const [file, setFile] = useState(null); // 위시등록 파일 상태
  const fileInputRef = useRef(null); // 파일 입력창 참조
  const [previewUrl, setPreviewUrl] = useState(null); //위시등록전송 url 상태
  const [uploading, setUploading] = useState(false); // 업로드 로딩 상태
  const [wishDetail, setWishDetail] = useState(false); //디테일창 상태
  const [cards, setCards] = useState([
    {
      id: 1,
      imgSrc:
        "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/iphone-card-40-iphone16prohero-202409?wid=680&hei=528&fmt=p-jpg&qlt=95&.v=1725567335931", // 아이템 이미지경로
      itemName: "test1", // 아이템 이름
      itemPrice: 1550000, // 아이템 가격
      progressRate: 50,
    },
    {
      id: 1,
      imgSrc:
        "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/iphone-card-40-iphone16prohero-202409?wid=680&hei=528&fmt=p-jpg&qlt=95&.v=1725567335931", // 아이템 이미지경로
      itemName: "test2", // 아이템 이름
      itemPrice: 1550000, // 아이템 가격
      progressRate: 50,
    },
    {
      id: 1,
      imgSrc:
        "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/iphone-card-40-iphone16prohero-202409?wid=680&hei=528&fmt=p-jpg&qlt=95&.v=1725567335931", // 아이템 이미지경로
      itemName: "test3", // 아이템 이름
      itemPrice: 1550000, // 아이템 가격
      progressRate: 50,
    },
  ]); // 슬릭 카드  수 상태

  const [selectedCard, setSelectedCard] = useState({
    imgSrc:
      "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/iphone-card-40-iphone16prohero-202409?wid=680&hei=528&fmt=p-jpg&qlt=95&.v=1725567335931", // 아이템 이미지경로
    itemName: "test3", // 아이템 이름
    itemPrice: 1550000, // 아이템 가격
    progressRate: 50, //진행률
  });
  //모달 오픈
  const inserModalOpen = () => {
    setModalOpen(true);
  };
  //모달 닫기
  const inserModalClose = () => {
    setModalOpen(false);
  };
  // 파일 입력창 활성화
  const handleActivateFileInput = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  // 파일 선택 핸들러
  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      setFile(selectedFile);
      setPreviewUrl(URL.createObjectURL(selectedFile));
    }
  };

  // 카드 추가 핸들러
  const addCard = () => {
    const newCard = {
      id: cards.length + 1,
      content: `Card ${cards.length + 1}`,
    };
    setCards([...cards, newCard]); // 기존 카드에 새로운 카드 추가
  };
  // 카드 삭제 핸들러
  const removeCard = () => {
    if (cards.length > 0) {
      setCards(cards.slice(0, -1)); // 마지막 카드 제거
    }
  };
  // Slick Slider 설정 옵션
  const settings = {
    dots: true, // 하단 점 네비게이션 활성화
    infinite: true, // 무한 스크롤 설정
    speed: 500, // 전환 속도 (ms)
    slidesToShow: 3, // 한 번에 보여질 슬라이드 수
    slidesToScroll: 1, // 한 번에 스크롤할 슬라이드 수
    responsive: [
      {
        breakpoint: 1000, // 화면 크기가 768px 이하일 경우
        settings: {
          slidesToShow: 1, // 슬라이드 1개씩 표시
        },
      },
    ],
  };
  //디데일 창 핸들러
  const showDetail = () => {
    setWishDetail(true);
  };

  return (
    <>
      <InsertWish onClick={inserModalOpen}>위시 등록하기</InsertWish>
      {/* 위시 생성 모달이 열렸을 때만 표시 */}
      {isModalOpen && (
        <Modal width="400px" height="600px">
          <h2>내 위시 등록</h2>
          {/* 이미지 미리보기 */}
          {previewUrl && (
            <div style={{ marginTop: "20px" }}>
              <InsertPreview src={previewUrl} alt="미리보기" />
            </div>
          )}
          {/* 파일 선택 */}
          <InsertImg onClick={handleActivateFileInput}>이미지등록</InsertImg>
          <input
            type="file"
            accept="image/*"
            ref={fileInputRef}
            onChange={handleFileChange}
            style={{ display: "none" }} // input을 숨김
          />
          <FormBox>
            <FormTitle>이름</FormTitle>
            <FormInput />
          </FormBox>
          <FormBox>
            <FormTitle>물품 가격</FormTitle>
            <FormInput />
          </FormBox>
          <InsertWishinModal onClick={inserModalClose}>
            내 위시 올리기
          </InsertWishinModal>
        </Modal>
      )}
      <div style={{ width: "80%", margin: "auto" }}>
        {/* Slick Slider */}
        <Slider {...settings}>
          {cards.map((card) => (
            <WishItemCard
              key={card.id}
              imgSrc={card.imgSrc}
              itemName={card.itemName}
              itemPrice={card.itemPrice}
              progressRate={card.progressRate}
              onClick={() => showDetail(card)}
            />
          ))}
        </Slider>
      </div>
      {wishDetail && <WishDetailBox selectedCard={selectedCard} />}
    </>
  );
};

const InsertPreview = styled.img`
    maxWidth: "30vw",
    maxHeight: "30vh",
    border: "1px solid #ccc",
    borderRadius: "10px",
`;
const InsertWish = styled.button`
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
const InsertImg = styled.button`
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
const FormBox = styled.div`
  display: flex;
  flex-direction: row;
  margin: 0 3vh 0 -5vh;
`;
const FormTitle = styled.h4`
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  color: black;
  margin: 0 0 0 1vh;
`;
const FormInput = styled.input`
  padding: 8px;
  border: 5px solid #c8bef3;
  border-radius: 10px;
  outline: none;
  maxwidth: 30vw;
`;
const InsertWishinModal = styled.button`
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
export default Activewish;
