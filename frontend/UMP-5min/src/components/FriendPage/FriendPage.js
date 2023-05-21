import React, { useState, useEffect } from "react";
import "./FriendPage.css";
import Toolbar from "../Toolbar";
import ToolbarButton from "../ToolbarButton";
import FriendSearch from "../FriendSearch";
import FriendList from "../FriendList/FriendList";
import axios from 'axios';
import { Input, Button } from "antd";

const FriendPage = (props) => {
  const [conversations, setConversations] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [friendId, setFriendId] = useState("");
  
  useEffect(() => {
    getConversations();
  }, []);
  
  const getConversations = () => {
    axios.get("https://randomuser.me/api/?results=20").then((response) => {
      let newConversations = response.data.results.map((result) => {
        return {
          photo: result.picture.large,
          name: `${result.name.first} ${result.name.last}`,
          text: "친구 정보",
        };
      });
      setConversations(newConversations);
    });
  };

  const openModal = () => {
    setShowAddModal(true);
  };

  const closeModal = () => {
    setShowAddModal(false);
  };

  const addFriend = (newFriendId) => {
    // 친구 추가 기능을 여기에 구현하세요.
  };

  const popoverContent = (
    <>
      <Input
        placeholder="ID를 입력하세요"
        value={friendId}
        onChange={(e) => setFriendId(e.target.value)}
      />
      <Button onClick={addFriend}>친구 추가</Button>
    </>
  );

  return (
    <div className="friend-list">
      <Toolbar
        title="Friend"
        leftItems={[<ToolbarButton key="cog" icon="ion-ios-cog" />]}
        rightItems={[
          <ToolbarButton key="add" icon="ion-ios-add-circle-outline" onClick={openModal} />,
        ]}
        popoverContent={popoverContent}
      />
      <FriendSearch />
      {conversations.map((conversation) => (
        <FriendList key={conversation.name} data={conversation} />
      ))}
    </div>
  );
};

export default FriendPage;
