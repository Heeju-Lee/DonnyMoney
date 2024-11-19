import React, { useRef, useContext, useState, useEffect } from "react";
import styled from "styled-components";
import Slider from "react-slick"; // react-slick 라이브러리 임포트
import "slick-carousel/slick/slick.css"; // slick 스타일시트 임포트
import "slick-carousel/slick/slick-theme.css";
import { WishItemCard } from "../../../commons/WishItemCard";
import axios from "axios";
import { AuthContext } from "../../../../App";
import { Modal } from "../../../commons/Modal";
import WishDetailBox from "./WishDetailBox";
import { PlanContext } from "../../../../pages/context/MoneyPlanContext";
const Activewish = (imgSrc) => {
  const { selectedCard, setSelectedCard } = useContext(PlanContext);
  const { memberNo, name, authorization } = useContext(AuthContext);
  const token = authorization;
  const [isModalOpen, setModalOpen] = useState(false); // 모달 열리고 닫고 상태 보관
  const [file, setFile] = useState(null); // 위시등록 파일 상태
  const fileInputRef = useRef(null); // 파일 입력창 참조
  const [previewUrl, setPreviewUrl] = useState(null); //위시등록전송 url 상태
  const [wishName, setWishName] = useState(""); // 위시생성 이름 상태
  const [wishPrice, setWishPrice] = useState(""); // 위시생성 가격 상태
  const [uploading, setUploading] = useState(false); // 업로드 로딩 상태
  const [wishDetail, setWishDetail] = useState(false); //디테일창 상태
  const [cards, setCards] = useState([]); // 슬릭 카드  수 상태
  // const [selectedCard, setSelectedCard] = useState(null); 
  

  const showSlick =()=>{
    axios({
      method: "GET",
      url: `http://localhost:9999/children/wishes/active`,
      headers: {
        Authorization: token, // Authorization 헤더에 토큰 추가
        "Content-Type": "multipart/form-data", // 데이터가 multipart/form-data 형식임을 명시
      },
    })
      .then((res) => {
        const formattedData = res.data.map((item) => ({
          id: item.wishNum, // wishNum을 id로 사용
          imgSrc: item.img, // 이미지 URL
          itemName: item.name, // 아이템 이름
          itemPrice: item.price, // 아이템 가격
          progressRate: (item.savingAmt / item.price) * 100 || 0, // 진행률 계산
          savingAmt:item.savingAmt, //저축한 돈
        }));
        setCards(formattedData);
      })
      .catch((err) => {
        console.error("위시 등록 실패:", err);
      });
  }

  //카드 넣기...
  const handleSubmitWish =async () => {
    if (!file || !wishName || !wishPrice) {
      alert("모든 필드를 입력해 주세요."); // 파일과 텍스트 필드가 모두 채워졌는지 확인
      return;
    }
    // FormData 객체 생성
    const formData = new FormData();
    formData.append(
      "wishRequestDtoJson",
      JSON.stringify({
        name: wishName,
        price: parseInt(wishPrice, 10),
      })
    );
    formData.append("wishFile", file); // 파일 첨부
    try {
      const response = await axios.post(
        "http://localhost:9999/children/wishes",
        formData,
        {
          headers: {
            Authorization: token, // Authorization 헤더에 토큰 추가
            "Content-Type": "multipart/form-data", // 데이터 형식 명시
          },
        }
      );
  
      console.log("위시 등록 성공:", response.data);
         // 데이터가 배열인지 객체인지 확인 후 처리
    if (Array.isArray(response.data)) {
      const newCards = response.data.map(item => ({
        id: item.wishNum,
        imgSrc: item.img,
        itemName: item.name,
        itemPrice: item.price,
        progressRate: 0,
        savingAmt:item.savingAmt,
      }));
      setCards(prevCards => [...prevCards, ...newCards]);
    } else {
      const newCard = {
        id: response.data.wishNum,
        imgSrc: response.data.img,
        itemName: response.data.name,
        itemPrice: response.data.price,
        progressRate: 0,
        savingAmt:response.data.savingAmt,
      };
      setCards(prevCards => [...prevCards, newCard]);
    }

      
      // 요청 성공 후 상태 초기화 및 모달 닫기
      setWishName(""); // 위시 이름 초기화
      setWishPrice(""); // 위시 가격 초기화
      setFile(null); // 파일 초기화
      setPreviewUrl(null); // 미리보기 URL 초기화
      setModalOpen(false); // 모달 닫기
      alert("위시가 성공적으로 등록되었습니다!");
    } catch (error) {
      console.error("위시 등록 실패:", error);
      alert("위시 등록에 실패했습니다. 다시 시도해 주세요.");
    }
  };
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

  // // 카드 추가 핸들러
  // const addCard = () => {
  //   const newCard = {
  //     id: cards.length + 1,
  //     content: `Card ${cards.length + 1}`,
  //   };
  //   setCards([...cards, newCard]); // 기존 카드에 새로운 카드 추가
  // };
  // // 카드 삭제 핸들러
  // const removeCard = () => {
  //   if (cards.length > 0) {
  //     setCards(cards.slice(0, -1)); // 마지막 카드 제거
  //   }
  // };
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
    // 카드 클릭 핸들러
  const showDetail = (card) => {
    setSelectedCard(card); // 클릭된 카드 데이터 설정
    setWishDetail(true); // 디테일 창 열기
  };
  // WishDetailBox 업데이트
  const handleSelectCard = (updateData) =>{
    console.log("handleSelectCard =========> ", updateData);
    /////////여기 상태를 수정해야함
    setSelectedCard({...updateData});
  }
  useEffect(() => {
    showSlick() 
  }, []);

  useEffect(() => {
    console.log("Selected card updated:", selectedCard);
    // 이 로직이 정말 필요한지 확인 후 사용
}, [selectedCard]);


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
            <FormInput
              type="text"
              value={wishName}
              onChange={(e) => setWishName(e.target.value)}
            />
          </FormBox>
          <FormBox>
            <FormPrice>물품 가격</FormPrice>
            <FormInput
              type="text"
              value={wishPrice}
              onChange={(e) => setWishPrice(e.target.value)}
            />
          </FormBox>
          <InsertWishinModal onClick={handleSubmitWish}>
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
      {wishDetail && <WishDetailBox selectedCard={selectedCard}  onCardUpdate={handleSelectCard}  />}
    </>
  );
};

const InsertPreview = styled.img`
  width: 20vw;
  height: 20vh;
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-buttom: 20px;
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
  margin-top: 10px;
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
  margin-top: 15px;
  margin-bottom: 5px;
  font-weight: bold;
`;
const FormBox = styled.div`
  display: flex;
  flex-direction: row;
  margin: 15px 0 10px 0;
  align-items: center;
`;
const FormTitle = styled.h4`
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  color: black;
  margin: 0 55px 0 0;
`;
const FormPrice = styled.h4`
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  color: black;
  margin: 0 5px 0 0;
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
