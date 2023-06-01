import React, { useState, useEffect } from "react";
import ConversationListItem from "../ConversationListItem/ConversationListItem";
import Toolbar from "../Toolbar/Toolbar";
import ToolbarButton from "../ToolbarButton/ToolbarButton";
import axios from "axios";
import { Button, Popover, Input } from "antd";
import "./ConversationList.css";
import ConversationSearch from "../ConversationSearch/ConversationSearch";

export default function ConversationList(props) {
  const [conversations, setConversations] = useState([]);
  const [inputValue, setInputValue] = useState("");

  const friends = ["admin1"];

  useEffect(() => {
    getConversations();
  }, []);

  const getConversations = () => {
    // axios.get("https://randomuser.me/api/?results=20").then((response) => {
    //   let newConversations = response.data.results.map((result) => {
    //     return {
    //       photo: `${result.name.last}`,
    //       name: `${result.name.first} ${result.name.last}`,
    //       text: "기존 채팅 기록",
    //     };
    //   });
    //   setConversations([...conversations, ...newConversations]);
    // });
    axios({
      method: "post",
      url: "/chattingroom",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        userIds: friends,
        roomName: inputValue,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        const newChatRoom = {
          name: inputValue,
        };
        setConversations([...conversations, newChatRoom]);
        setInputValue("");
      })
      .catch((error) => {
        if (error.response.status === 400) {
          if (
            error.response.data === "해당 유저가 존재하지 않습니다." ||
            error.response.data === "이미 해당 유저에게 친구 요청을 하였습니다."
          ) {
            alert(error.response.data);
          }

          console.log(error.response.data.message);
        } else {
          console.log("기타 에러 발생");
        }
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
      });
  };

  const handleAddChatRoom = () => {
    if (inputValue === "") {
      alert(`채팅방 이름을 입력해주세요`);
      return;
    }
    axios({
      method: "post",
      url: "/chattingroom",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        userIds: friends,
        roomName: inputValue,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        const newChatRoom = {
          name: inputValue,
        };
        setConversations([...conversations, newChatRoom]);
        setInputValue("");
      })
      .catch((error) => {
        if (error.response.status === 400) {
          if (
            error.response.data === "해당 유저가 존재하지 않습니다." ||
            error.response.data === "이미 해당 유저에게 친구 요청을 하였습니다."
          ) {
            alert(error.response.data);
          }

          console.log(error.response.data.message);
        } else {
          console.log("기타 에러 발생");
        }
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
      });
  };

  const popoverContent = (
    <>
      <Input
        placeholder="채팅방 이름을 입력해주세요"
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
      />
      <Button onClick={handleAddChatRoom}>채팅방 추가</Button>
    </>
  );

  return (
    <div className="conversation-list">
      <Toolbar
        title="Messenger"
        leftItems={[<ToolbarButton key="cog" icon="ion-ios-cog" />]}
        rightItems={[
          <ToolbarButton
            key="add"
            icon="ion-ios-add-circle-outline"
            onClick={handleAddChatRoom}
          />,
        ]}
        popoverContent={popoverContent}
      />
      <ConversationSearch />
      {conversations.map((conversation) => (
        <ConversationListItem key={conversation.name} data={conversation} />
      ))}
    </div>
  );
}
