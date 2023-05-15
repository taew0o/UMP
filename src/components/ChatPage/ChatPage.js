import React from "react";
import ConversationList from "../ConversationList";
import { useNavigate } from "react-router-dom";

const ChatPage = () => {
  const isLogin = true; // 로그인 됐는지는 서버 연결하면 제대로 바꿈
  const navigate = useNavigate();
  const toLogin = () => {
    navigate("/login");
  };
  return <div>{isLogin ? <ConversationList /> : toLogin()}</div>;
};

export default ChatPage;
