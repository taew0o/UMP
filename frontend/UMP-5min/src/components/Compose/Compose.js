import React, { useState } from "react";
import "./Compose.css";

export default function Compose(props) {
  const [msg, setMsg] = useState();

  const sendMessage = (e) => {
    props.getText({
      author: props.MY_USER_ID,
      message: msg,
      timestamp: new Date().getTime(),
    });
  };

  return (
    <div className="compose">
      <form onSubmit={sendMessage}>
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
              sendMessage(e);
              setMsg("");
            }
          }}
        />
        {/* <input type="submit" value="제출" /> */}
      </form>

      {props.rightItems}
    </div>
  );
}
