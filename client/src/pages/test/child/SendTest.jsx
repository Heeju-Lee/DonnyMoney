import React, { useEffect, useState } from "react";
import axios from "axios"; // Axios 추가

/**
 * TEST 페이지
 */
export default function SendTest() {
  const [eventSource, setEventSource] = useState(null); // SSE 연결상태
  const [notifications, setNotifications] = useState([]); // 알림 목록 상태

  const role = "ROLE_CHILD"; // test용
  const userNum = 3; // test용

  // 기존 알림 목록 가져오기
  const fetchNotifications = async () => {
    try {
      const response = await axios.get(
        `http://localhost:9999/child-notification/${userNum}`
      );
      setNotifications(response.data); // 기존 알림 목록을 상태에 저장
    } catch (error) {
      console.error("알림 조회 에러", error);
    }
  };

  // 아이가 부모에게 알림 전송
  const sendNotificationToParent = async () => {
    const notificationData = {
      parentNum: 1, // 부모 ID
      childNum: 3, // 아이 ID
      message: "소비계획서가 도착했습니다~!33",
      category: "info",
      senderType: "child",
    };

    const response = await fetch("/sendToParent", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(notificationData),
    });

    if (response.ok) {
      console.log("부모에게 알림 전송 성공!");
    } else {
      console.error("부모에게 알림 전송 실패!");
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
      console.error("[SSE] 에러발생: ", err);
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
      <h1>알림 테스트 - 아이페이지</h1>
      <button onClick={sendNotificationToParent}>
        부모님에게 알림 전송하기
      </button>
      <br />
      <p>부모님으로부터 도착한 알림 목록</p>
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
