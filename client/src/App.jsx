import { BrowserRouter } from "react-router-dom";
import { GlobalStyle } from "./styles/GlobalStyle";
import Header from "./components/layouts/Header";
import Navirouter from "./Navirouter";
import "bootstrap/dist/css/bootstrap.min.css";
import Layout from "./components/layouts/Layout";

// 역할과 사용자 ID (role : ROLE_PARENT , ROLE_CHILD)
// 로그인 정보가 없으면 로그인 페이지로 이동
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        {/* 전역 스타일 */}
        <GlobalStyle />
        {/* 헤더 (공통) */}
        <Header />
        <Layout>
          {/* 각 페이지 */}
          <Navirouter />
        </Layout>
      </BrowserRouter>
    </div>
  );
}

export default App;
