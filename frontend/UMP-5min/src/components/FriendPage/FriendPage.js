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
  const [friends, setFriends] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showRequestsModal, setShowRequestsModal] = useState(false);
  const [friendId, setFriendId] = useState();
  const [friendRequests, setFriendRequests] = useState([]);

  useEffect(() => {
    getFriend();
    getFriendRequestList();
  }, []);

  // useEffect(() => {
  //   getFriend();
  // }, [friendRequests]);

  const openModal = () => {
    setShowAddModal(true);
  };

  const closeModal = () => {
    setShowAddModal(false);
  };

  const openRequestsModal = () => {
    getFriendRequestList();
    setShowRequestsModal(true);
  };

  const closeRequestsModal = () => {
    setShowRequestsModal(false);
  };

  const getFriend = () => {
    axios({
      method: "get",
      url: "/friends",
      headers: {
        "Content-Type": `application/json`,
      },
      withCredentials: true,
    })
      .then((response) => {
        let newFriend = response.data.map((result) => {
          return {
            id: `${result.id}`,
            name: `${result.name}`,
            text: "친구 정보",
            appointmentScore: `${result.appointmentScore}`,
          };
        });
        setFriends(newFriend);
      })
      .catch((error) => {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
      });
  };
  const getFriendRequestList = () => {
    axios({
      method: "get",
      url: "/friend-requests",
      headers: {
        "Content-Type": `application/json`,
      },
      withCredentials: true,
    })
      .then((response) => {
        // console.log("----------------", response);
        const newFriendRequests = response.data.map((value) => {
          return { id: value.id, name: value.name };
        });
        setFriendRequests((prevRequests) => {
          // 중복 값 제거
          const filteredRequests = newFriendRequests.filter(
            (request) =>
              !prevRequests.some((prevRequest) => prevRequest.id === request.id)
          );
          // 기존 요청과 새로운 요청을 합친 배열 반환
          return [...prevRequests, ...filteredRequests];
        });
      })
      .catch((error) => {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
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
        alert(`에러 발생 관리자 문의하세요!`);
      });
  };

  const acceptRequest = (requestId) => {
    // 요청 기능 구현해야됨
    console.log(`Accepted request from ${requestId}`);
    setFriendRequests((prevRequests) =>
      prevRequests.filter((request) => request.id !== requestId)
    );
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
        getFriend();
      })
      .catch((error) => {
        console.log(error);
        // alert(`에러 발생 관리자 문의하세요!`);
      });
  };

  const declineRequest = (requestId) => {
    // 거절 기능
    setFriendRequests((prevRequests) =>
      prevRequests.filter((request) => request.id !== requestId)
    );
    console.log(`Declined request from ${requestId}`);
    axios({
      method: "post",
      url: "/friend-response",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        isAccept: false,
        senderId: requestId,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        getFriend();
      })
      .catch((error) => {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
      });
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
        {friendRequests.length === 0 ? (
          <div
            style={{
              marginBottom: 12,
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            친구 요청이 없습니다
          </div>
        ) : (
          friendRequests.map((request) => (
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
          ))
        )}
      </Modal>

      <FriendSearch />
      {friends.map((friend) => (
        <FriendList key={friend.name} data={friend} />
      ))}
    </div>
  );
};

export default FriendPage;
