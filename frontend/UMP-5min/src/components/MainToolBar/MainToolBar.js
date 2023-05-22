import React, { useState, useEffect } from "react";
import ConversationSearch from "../ConversationSearch";
import ConversationListItem from "../ConversationListItem";
import Toolbar from "../Toolbar";
import ToolbarButton from "../ToolbarButton";
import axios from "axios";

import "./ConversationList.css";
import { Button } from "antd";
import { useNavigate } from "react-router-dom";

export default function MainToolBar(props) {
  const navigate = useNavigate();
  const toPage = (data) => {
    navigate("/" + data);
  };
  const onClickHandler = (e) => toPage(e.target.value);
  return (
    <div className="conversation-list">
      {/* 각 탭으로 이동 */}
      <Button value="friend" onClick={(e) => onClickHandler(e)}>
        친구
      </Button>
      <Button value="" onClick={(e) => onClickHandler(e)}>
        채팅
      </Button>
      <Button value="calender" onClick={(e) => onClickHandler(e)}>
        캘린더
      </Button>
      <Button value="setting" onClick={(e) => onClickHandler(e)}>
        설정
      </Button>
    </div>
  );
}
