import React, { useState, useEffect } from "react";
import ConversationListItem from "../ConversationListItem/ConversationListItem";
import Toolbar from "../Toolbar/Toolbar";
import ToolbarButton from "../ToolbarButton/ToolbarButton";
import axios from "axios";
import { Button, Popover, Input } from "antd";
import "./ConversationList.css";
import ConversationSearch from "../ConversationSearch/ConversationSearch";
import { Checkbox } from "antd"; 

export default function ConversationList(props) {
  const [conversations, setConversations] = useState([]);
  const [visible, setVisible] = useState(false);
  const [inputValue, setInputValue] = useState("");
  // 친구 목록 state
  const [friends, setFriends] = useState([]);

  useEffect(() => {
    getConversations();
    getFriends();
  }, []);

  const getConversations = () => {
    axios.get("https://randomuser.me/api/?results=20").then((response) => {
      let newConversations = response.data.results.map((result) => {
        return {
          photo: `${result.name.last}`,
          name: `${result.name.first} ${result.name.last}`,
          text: "기존 채팅 기록",
        };
      });
      setConversations([...conversations, ...newConversations]);
    });
  };

  // 친구 목록을 가져오는 함수
  const getFriends = () => {
    axios({
      method: "get",
      url: "/friends",
      headers: {
        "Content-Type": `application/json`,
      },
      withCredentials: true,
    })
      .then((response) => {
        let newFriends = response.data.map((result) => {
          return {
            id: `${result.id}`,
            name: `${result.name}`,
            text: "친구 목록",
            appointmentScore: result.appointmentScore,
          };
        });
        setFriends(newFriends);
      })
      .catch((error) => {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
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

  const handleShowFriends = () => {
    setVisible(!visible);
  };

  const renderFriendsList = () => {
    return (
   <div>
        {friends.map((friend) => (
          <div key={friend.id}>
            <Checkbox
              onChange={(e) => handleSelectFriend(e, friend)}
              checked={inputValue.includes(friend.name)}
            >
              {friend.name}
            </Checkbox>
          </div>
        ))}
      </div>
    );
  };
  

  const handleSelectFriend = (e, selectedFriend) => {
    if (e.target.checked) {
      setInputValue([...inputValue, selectedFriend.name].join(", "));
    } else {
      setInputValue(
        inputValue
          .split(", ")
          .filter((friendName) => friendName !== selectedFriend.name)
          .join(", ")
      );
    }
  };

  const popoverContent = (
    <>
      <Input
        placeholder="채팅방에 이름을 입력해주세요"
        
        onChange={(e) => setInputValue(e.target.value)}
      />
      <Button onClick={handleAddChatRoom}>채팅방 추가</Button>
      <Button onClick={handleShowFriends}>친구 목록</Button>
      {visible && renderFriendsList()}
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
