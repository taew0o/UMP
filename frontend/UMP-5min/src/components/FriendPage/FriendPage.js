import React, { useState, useEffect } from "react";
import "./FriendPage.css";
import Toolbar from "../Toolbar/Toolbar";
import ToolbarButton from "../ToolbarButton/ToolbarButton";
import FriendSearch from "../FriendSearch/FriendSearch";
import FriendList from "../FriendList/FriendList";
import axios from "axios";
import { Input, Button, Modal } from "antd";
import { UserAddOutlined } from "@ant-design/icons";

const FriendPage = (props) => {
  const [conversations, setConversations] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showRequestsModal, setShowRequestsModal] = useState(false);
  const [friendId, setFriendId] = useState();
  const [friendRequests, setFriendRequests] = useState([]);

  useEffect(() => {
    getConversations();
    getFriendList();
    // console.log(getFriendList());
    // setFriendRequests([
    //   { id: "user1", name: "John Doe" },
    //   { id: "user2", name: "Jane Smith" },
    // ]);
  }, []);

  const getConversations = () => {
    // axios.get("https://randomuser.me/apix/?results=20").then((response) => {
    //   let newConversations = response.data.results.map((result) => {
    //     return {
    //       photo: `${result.name.last}`,
    //       name: `${result.name.first} ${result.name.last}`,
    //       text: "친구 정보",
    //     };
    //   });
    //   setConversations(newConversations);
    // });
  };

  const openModal = () => {
    setShowAddModal(true);
  };

  const closeModal = () => {
    setShowAddModal(false);
  };

  const openRequestsModal = () => {
    setShowRequestsModal(true);
  };

  const closeRequestsModal = () => {
    setShowRequestsModal(false);
  };

  const getFriendList = () => {
    axios({
      method: "get",
      url: "/friend-requests",
      headers: {
        "Content-Type": `application/json`,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        const newFriendRequests = response.data.map((value) => {
          return { id: value.id, name: value.name };
        });
        setFriendRequests([...friendRequests, ...newFriendRequests]);
      })
      .catch((error) => {
        console.log(error);
        // alert(`에러 발생 관리자 문의하세요!`);
      });
  };

  const addFriend = () => {
    // 친구 추가 기능을 여기에 구현

    axios({
      method: "post",
      url: "/friend-request",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        friendId: friendId,
      },
      withCredentials: true,
    })
      .then((response) => {
        alert(`${response.data.name}님에게 친구요청을 보냈습니다.`);
        console.log("----------------", response);
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
        // alert(`에러 발생 관리자 문의하세요!`);
      });
  };

  const acceptRequest = (requestId) => {
    // 요청 기능 구현해야됨
    console.log(`Accepted request from ${requestId}`);
    // setFriendRequests(
    //   friendRequests.filter((request) => request.id !== requestId)
    // );
    axios({
      method: "post",
      url: "/friend-response",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        isAccept: true,
        senderId: requestId,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
      })
      .catch((error) => {
        console.log(error);
        // alert(`에러 발생 관리자 문의하세요!`);
      });
  };

  const declineRequest = (requestId) => {
    // 거절 기능
    console.log(`Declined request from ${requestId}`);
    setFriendRequests(
      friendRequests.filter((request) => request.id !== requestId)
    );
  };

  const popoverContent = (
    <>
      <Input
        placeholder="ID를 입력하세요"
        value={friendId}
        onChange={(e) => setFriendId(e.target.value)}
        onKeyDown={(e) => {
          if (e.key === "Enter") {
            addFriend();
          }
        }}
      />
      <Button onClick={addFriend}>친구 추가</Button>
    </>
  );

  return (
    <div className="friend-list">
      <Toolbar
        title="Friend"
        leftItems={[
          <UserAddOutlined
            style={{ fontSize: "24px", color: "#1C75D4" }}
            onClick={openRequestsModal}
          />,
        ]}
        rightItems={[
          <ToolbarButton
            key="add"
            icon="ion-ios-add-circle-outline"
            onClick={openModal}
          />,
        ]}
        popoverContent={popoverContent}
      />

      {/* Friend Requests Modal */}
      <Modal
        title="Friend Requests"
        visible={showRequestsModal}
        onCancel={closeRequestsModal}
        footer={null}
      >
        {friendRequests.map((request) => (
          <div
            key={request.id}
            style={{
              marginBottom: 12,
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            <span>{request.name + "(" + request.id + ")"}</span>
            <div>
              <Button
                onClick={() => acceptRequest(request.id)}
                style={{ marginLeft: 8 }}
              >
                수락
              </Button>
              <Button
                onClick={() => declineRequest(request.id)}
                style={{ marginLeft: 8 }}
              >
                거절
              </Button>
            </div>
          </div>
        ))}
      </Modal>

      <FriendSearch />
      {conversations.map((conversation) => (
        <FriendList key={conversation.name} data={conversation} />
      ))}
    </div>
  );
};

export default FriendPage;
