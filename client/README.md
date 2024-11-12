```text
// 프로젝트 루트 디렉토리 (컴포넌트 위주 작성)
app/
├── public // 이미지나 폰트 파일등 정적 파일 관리
│      ├── images/
│      ├── index.html
│      └── ...
│
├── src/
│   ├── components/ (컴포넌트)
│   │   └── commons/ (공통 UI 컴포넌트)
│   │   │   ├── Button.js
│   │   │   ├── Input.js
│   │   │   └── ...
│   │   │
│   │   ├── pages/  (각 페이지 영역 컴포넌트)
│   │   │   └── Mgmt/ (Mgmt 페이지에 사용되는 영역 컴포넌트)
│   │   │       ├── Login.js (로그인 영역)
│   │   │       └── ...
│   │   │
│   │   └── layouts/ (페이지 공통 레이아웃)
│   │       ├── Header.js
│   │       ├── Footer.js
│   │       └── ...
│   │
│   ├── pages/
│   │   ├── parent/ (부모 전용 페이지)
│   │   │   ├──  ParentHomePage.js
│   │   │   └── ...
│   │   │
│   │   ├── child/ (아이 전용 페이지)
│   │   │   ├── ChildHomePage.js
│   │   │   └── ...
│   │   │
│   │   └── common/ (공통 페이지)
│   │       ├── HomePage.js
│   │       ├── LoginPage.js
│   │       ├── NotFound.js
│   │       └── ...
│   │
│   ├── context/ (전역 상태 관리)
│   │   ├──  AuthContext.js (사용자 인증 상태관리)
│   │   └── ...
│   │
│   ├── services/  (api 함수 (Axios, 외부 API))
│   │   ├──  api.js
│   │   └── ...
│   │
│   └── styles/  // 스타일 관리 파일
│       ├──  GlobalStyles.js  // 전역 스타일(배경, 폰트 등)
│       └── ...
│
├── App.js
└── index.js
```
