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
  const [visible, setVisible] = useState(false);
  const [searchValue, setSearchValue] = useState(""); // New state for search value
  const [filteredConversations, setFilteredConversations] = useState([]); // New state for filtered conversations

  useEffect(() => {
    getConversations();
    getFriends();
    console.log(friends);
  }, []);

  useEffect(() => {
    filterConversations();
  }, [searchValue, conversations]);

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
        alert(error.response.data);
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
          return { id: result.id, name: result.name };
        });
        setFriends(newFriends);
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data);
      });
  };

  const handleAddChatRoom = () => {
    getFriends();
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
        userIds: selectedFriends,
        roomName: inputValue,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        setInputValue("");
        setVisible(false);
        setSelectedFriends([]);
        getConversations();
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data);
        console.log(friends);
      });
  };

  const handleShowFriends = () => {
    setVisible(!visible);
  };

  const renderFriendsList = () => {
    return (
      <div>
        {friends.map((friend, index) => (
          <div key={index}>
            <Checkbox
              onChange={(e) => handleSelectFriend(e, friend.id)}
              checked={selectedFriends.includes(friend.id)}
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
      setSelectedFriends([...selectedFriends, selectedFriend]);
    } else {
      setSelectedFriends(
        selectedFriends.filter((friendName) => friendName !== selectedFriend)
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

  const handleSearch = (e) => {
    setSearchValue(e.target.value);
  };

  const filterConversations = () => {
    const filtered = conversations.filter((conversation) => {
      return conversation.name
        .toLowerCase()
        .includes(searchValue.toLowerCase());
    });
    setFilteredConversations(filtered);
  };

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
      <ConversationSearch handleSearch={handleSearch} />
      {filteredConversations.length === 0 ? (
        <div style={{ textAlign: "center", marginTop: "20px" }}>
          오른쪽 상단 플러스 버튼을 눌러 채팅방을 추가해주세요
        </div>
      ) : (
        filteredConversations.map((conversation) => (
          <ConversationListItem key={conversation.name} data={conversation} />
        ))
      )}
    </div>
  );
}
