import React, { useState } from "react";
import EduNav from "../../components/pages/child/edu/EduNav";
import EduVideo from "../../components/pages/child/edu/EduVideo";
import EduQuiz from "../../components/pages/child/edu/EduQuiz";

const EduPage = () => {
  const [selectedCategory, setSelectedCategory] = useState("영상교육"); // 선택된 카테고리

  const renderContent = () => {
    switch (selectedCategory) {
      case "퀴즈":
        return <EduQuiz />;
      case "영상교육":
      default:
        return <EduVideo />;
    }
  };

  return (
    <>
      <EduNav onSelect={setSelectedCategory} />
      {renderContent()}
    </>
  );
};

export default EduPage;
