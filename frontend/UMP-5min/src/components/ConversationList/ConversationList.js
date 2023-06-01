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
  const [inputValue, setInputValue] = useState("");
  const [friends, setFriends] = useState([]);
  const [selectedFriends, setSelectedFriends] = useState([]);

  useEffect(() => {
    getConversations();
    getFriends();
  }, []);

  const getConversations = () => {
    axios({
      method: "get",
      url: "/chattingrooms",
      headers: {
        "Content-Type": `application/json`,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        const newConversations = response.data.reduce((acc, value) => {
          if (value.chattingRoomName !== "") {
            const newChatRoom = {
              name: value.chattingRoomName,
              member: value.numPerson,
              createTime: value.createTime,
              id: value.id,
            };
            acc.push(newChatRoom);
          }
          return acc;
        }, []);
        setConversations(newConversations);
      })
      .catch((error) => {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
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
    if (inputValue === "") {
      alert("채팅방 이름을 입력해주세요");
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
        setInputValue("");
        getConversations();
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
              checked={selectedFriends.includes(friend.name)}
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
      setSelectedFriends([...selectedFriends, selectedFriend.name]);
    } else {
      setSelectedFriends(
        selectedFriends.filter(
          (friendName) => friendName !== selectedFriend.name
        )
      );
    }
  };
  const popoverContent = (
    <>
      <Input
        placeholder="채팅방 이름을 입력해주세요"
        value={inputValue}
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
            onClick={handleShowFriends}
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
