// SSE 사용하기
const eventSource = new EventSource(`/subscribe`); // sse 코드가 있는 서버단 파일

// 서버로부터 데이터가 오면
eventSource.addEventListener("message", function (e) {
  console.log("meassage");
  console.log(e.data);
});

// connections 되면
eventSource.addEventListener("open", function (e) {
  console.log("open");
});

// error 나면
eventSource.addEventListener("error", function (e) {
  console.log("error");

  if (e.readyState === EventSource.CLOSED) {
  }
});
