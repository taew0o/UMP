import React, { useEffect, useRef, useState } from "react";
import "./Compose.css";

export default function Compose(props) {
  const [msg, setMsg] = useState("");

  const scrollRef = useRef();

  useEffect(() => {
    scrollToBottom();
  }, []);

  const scrollToBottom = () => {
    if (scrollRef.current) {
      scrollRef.current.scrollIntoView({ behavior: "smooth", block: "end" });
    }
  };

  const sendMessage = (e) => {
    // 메시지가 비어있는지 확인하세요.
    if (!msg.trim()) {
      return;
    }

    props.getText({
      author: props.MY_USER_ID,
      message: msg,
      timestamp: new Date().getTime(),
      name: props.MY_NAME,
    });
  };

  return (
    <div className="compose" ref={scrollRef}>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          sendMessage();
          setMsg("");
        }}
      >
        <input
          type="text"
          className="compose-input"
          placeholder="Type a message"
          value={msg}
          onChange={(e) => {
            setMsg(e.target.value);
          }}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              e.preventDefault();
              sendMessage();
              // 메시지가 비어있지 않으면 인풋을 비워주세요.
              if (msg.trim()) {
                setMsg("");
              }
            }
          }}
        />
      </form>

      {props.rightItems}
    </div>
  );
}
