import React, { useState, useEffect } from "react";
import ConversationSearch from "../ConversationSearch";
import ConversationListItem from "../ConversationListItem";
import Toolbar from "../Toolbar";
import ToolbarButton from "../ToolbarButton";
import axios from "axios";
import { Button, Popover, Input } from "antd";
import "./ConversationList.css";

export default function ConversationList(props) {
  const [conversations, setConversations] = useState([]);
  const [visible, setVisible] = useState(false);
  const [inputValue, setInputValue] = useState("");

  useEffect(() => {
    getConversations();
  }, []);

  const getConversations = () => {
    axios.get("https://randomuser.me/api/?results=20").then((response) => {
      let newConversations = response.data.results.map((result) => {
        return {
          photo: result.picture.large,
          name: `${result.name.first} ${result.name.last}`,
          text: "Hello world! This is a long message that needs to be truncated.",
        };
      });
      setConversations([...conversations, ...newConversations]);
    });
  };

  const handleAddChatRoom = () => {
    const newChatRoom = {
      name: inputValue,
    };
    setConversations([...conversations, newChatRoom]);
    setInputValue("");
    setVisible(false);
  };

  const popoverContent = (
    <>
      <Input
        placeholder="채팅방에 함께할 친구들을 입력해주세요"
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
