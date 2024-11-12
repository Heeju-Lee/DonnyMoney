import React, { useEffect, useState } from "react";
// import { useAuth } from "./AuthContext";
import axios from "axios"; // Axios 추가

/**
 *  SSE 연결
    클라이언트는 /subscribe/{id}를 호출하여 서버와 SSE 연결을 설정
    서버는 SseEmitter를 생성하고 저장하여 클라이언트와 실시간 통신을 유지
    부모나 아이가 /sendToChild 또는 /sendToParent를 호출하여 알림을 보냄
    서버는 알림 데이터를 저장하고, SseEmitter를 통해 해당 클라이언트에게 알림을 실시간으로 전송
 */
/**
 * 부모 TEST 페이지
 */
export default function MonthReport() {
  // const { authState, logout } = useAuth();
  const [eventSource, setEventSource] = useState(null); // SSE 연결상태
  const [notifications, setNotifications] = useState([]); // 알림 목록 상태

  const role = "ROLE_PARENT"; // test용
  const userNum = 1; // test용

  // 기존 알림 목록 가져옴
  const fetchNotifications = async () => {
    try {
      const response = await axios.get(
        `http://localhost:9999/parent-notificaiton/${userNum}`
      );
      setNotifications(response.data); // 기존 알림 목록을 상태에 저장
    } catch (error) {
      console.error("알림 조회 에러", error);
    }
  };

  // 부모가 아이에게 알림 전송
  const sendNotificationToParent = async () => {
    const notificationData = {
      parentNum: 1, // 부모 ID
      childNum: 3, // 아이 ID
      message: "부모님의 한마디66",
      category: "info",
      senderType: "parent",
    };

    const response = await fetch("/sendToChild", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(notificationData),
    });

    if (response.ok) {
      console.log("아이에게 알림 전송 성공!");
    } else {
      console.error("아이에게 알림 전송 실패!");
    }
  };

  useEffect(() => {
    fetchNotifications();

    if (eventSource) {
      console.log("[SSE] 이미 연결되었습니다.");
      return;
    }

    // const sse = new EventSource(`/subscribe/${userNum}`);
    const sse = new EventSource(`http://localhost:9999/subscribe/${userNum}`);

    sse.onopen = () => {
      console.log(`[SSE] ${role}로서 연결됨`);
    };

    sse.onmessage = (event) => {
      if (event.data === "ping") {
        // 'ping' 이벤트는 무시하거나 연결 상태를 확인하는 데 사용
        console.log("[SSE] Keep-alive ping received");
      } else {
        console.log("[SSE] 받은 알림데이터:", event.data);
      }

      try {
        // console.log("Event Data:", event.data); // 이벤트 데이터 확인
        const notification = JSON.parse(event.data);
        // console.log(`[SSE] Notification received:`, notification);

        setNotifications((prevNotifications) => [
          notification,
          ...prevNotifications,
        ]);
      } catch (error) {
        console.error("Error parsing message:", error);
      }
    };

    sse.onerror = (err) => {
      console.error("[SSE] Error occurred: ", err);
    };

    setEventSource(sse);

    return () => {
      console.log("[SSE] Cleaning up connection...");
      sse.close();
      console.log("[SSE] Connection closed.");
    };
  }, []);

  return (
    <>
      <div>알림 테스트 - 부모페이지</div>
      <button onClick={sendNotificationToParent}>아이에게 알림 전송하기</button>
      <br />
      <p>아이로부터 도착한 알림 목록</p>
      <ul>
        {notifications.map((notification, index) => (
          <li key={index}>
            {notification.message} - {notification.category}
          </li>
        ))}
      </ul>
    </>
  );
}
