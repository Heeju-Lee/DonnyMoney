import React, { createContext,  useState, } from "react"; 
import axios from "axios";


const WishContext = createContext();

// PlanProvider 컴포넌트
const WishProvider = ({ wish }) => {
    const [selectedCard, setSelectedCard] = useState(null); // 선택된 카드 상태
  
  return (
    <WishContext.Provider value={{selectedCard, setSelectedCard }}>
      {wish }
    </WishContext.Provider>
  );
};


export default  WishProvider;


export { WishContext };