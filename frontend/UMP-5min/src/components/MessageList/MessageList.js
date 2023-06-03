import React, { useCallback, useEffect, useRef, useState } from "react";
import Compose from "../Compose/Compose";
import Toolbar from "../Toolbar/Toolbar";
import ToolbarButton from "../ToolbarButton/ToolbarButton";
import Message from "../Message/Message";
import moment from "moment";

import "./MessageList.css";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import ReactModal from "react-modal";
import Review from "../Review/Review";
import { Button, Checkbox } from "antd";
import axios from "axios";

//채팅방 구현
export default function MessageList({ props }) {
  const MY_USER_ID = props.id;
  const MY_NAME = props.name;

  const [messages, setMessages] = useState([]);
  const navigate = useNavigate();
  const location = useLocation();
  const { id } = useParams();
  const state = location.state;

  const [result, setResult] = useState([]);
  const [text, setText] = useState();

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [reviewIsOpen, setReviewIsOpen] = useState(false);

  const [socketConnected, setSocketConnected] = useState(false);

  const [friends, setFriends] = useState([]);
  const [selectedFriends, setSelectedFriends] = useState([]);
  const [visible, setVisible] = useState(false);

  const webSocketUrl = "ws://localhost:8080/websocket?roomId=" + id;
  const ws = useRef(null);

  useEffect(() => {
    getFriends();
    getMessages();
    localStorage.setItem("location", "room");
    if (!ws.current) {
      ws.current = new WebSocket(webSocketUrl);
      ws.current.onopen = () => {
        console.log("connected to " + webSocketUrl);
        setSocketConnected(true);
      };
      ws.current.onclose = (error) => {
        console.log("disconnect from " + webSocketUrl);
        console.log(error);
      };
      ws.current.onerror = (error) => {
        console.log("connection error " + webSocketUrl);
        console.log(error);
      };
      ws.current.onmessage = (evt) => {
        const data = JSON.parse(evt.data);
        console.log(data);
        const tempMsg = {
          author: data.senderId,
          message: data.textMsg,
          timestamp: data.sendTime,
          name: data.sendName,
        };
        setMessages((prevMessages) => [...prevMessages, tempMsg]);
      };
    }

    return () => {
      console.log("clean up");
      localStorage.setItem("location", "notRoom");
      ws.current.close();
    };
  }, []);

  useEffect(() => {
    console.log("My id???????????????", MY_USER_ID);
    console.log(text);
    makeMsg();
    if (socketConnected && text) {
      ws.current.send(
        JSON.stringify({
          roomId: id,
          textMsg: text.message,
          sendTime: new Date().getTime(),
          senderId: MY_USER_ID,
          sendName: MY_NAME,
        })
      );
      console.log("메시지 보낸다");
    }
    console.log("message!!!!!!!", messages);
  }, [text]);

  useEffect(() => {
    renderMessages();
  }, [messages]);

  const getText = (prop) => {
    setText(prop);
  };

  const makeMsg = () => {
    if (text) {
      setMessages([...messages, text]);
    }
  };

  const getMessages = () => {
    axios({
      method: "get",
      url: "/chattingroom/messages",
      headers: {
        "Content-Type": `application/json`,
      },
      params: { roomId: id },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        response.data.map((value) => {
          const tempMsg = {
            author: value.senderId,
            message: value.textMsg,
            timestamp: value.sendTime,
            name: value.sendName,
          };
          setMessages((prevMessages) => [...prevMessages, tempMsg]);
        });
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data);
      });
  };

  const outRoom = () => {
    axios({
      method: "delete",
      url: "/chattingroom/member",
      headers: {
        "Content-Type": `application/json`,
      },
      params: { roomId: id },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
        // ws.current.send(
        //   JSON.stringify({
        //     roomId: id,
        //     textMsg: text.message,
        //     sendTime: new Date().getTime(),
        //     senderId: MY_USER_ID,
        //   })
        // );
        navigate("/");
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data);
      });
  };

  const renderMessages = () => {
    let i = 0;
    let messageCount = messages.length;
    let tempMessages = [];

    while (i < messageCount) {
      let previous = messages[i - 1];
      let current = messages[i];
      let next = messages[i + 1];
      let isMine = current.author === MY_USER_ID;
      let currentMoment = moment(current.timestamp);
      let prevBySameAuthor = false;
      let nextBySameAuthor = false;
      let startsSequence = true;
      let endsSequence = true;
      let showTimestamp = true;

      if (previous) {
        let previousMoment = moment(previous.timestamp);
        let previousDuration = moment.duration(
          currentMoment.diff(previousMoment)
        );
        prevBySameAuthor = previous.author === current.author;

        if (prevBySameAuthor && previousDuration.as("hours") < 1) {
          startsSequence = false;
        }

        if (previousDuration.as("hours") < 1) {
          showTimestamp = false;
        }
      }

      if (next) {
        let nextMoment = moment(next.timestamp);
        let nextDuration = moment.duration(nextMoment.diff(currentMoment));
        nextBySameAuthor = next.author === current.author;

        if (nextBySameAuthor && nextDuration.as("hours") < 1) {
          endsSequence = false;
        }
      }

      const senderName = isMine ? MY_NAME : current.name;

      tempMessages.push(
        <Message
          key={i}
          isMine={isMine}
          startsSequence={startsSequence}
          endsSequence={endsSequence}
          showTimestamp={showTimestamp}
          data={current}
          senderName={senderName} // Pass senderId as senderName prop
        />
      );

      // Proceed to the next message.
      i += 1;
    }
    setResult(tempMessages);
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
        <Button onClick={addFriend}>초대</Button>
      </div>
    );
  };

  const addFriend = () => {
    axios({
      method: "post",
      url: "/chattingroom/member",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        roomId: id,
        inviteeIds: selectedFriends,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("addFriend response", response);
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

  const handleShowFriends = () => {
    setVisible(!visible);
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
  return (
    <div className="message-list">
      <Toolbar
        title={state.name}
        rightItems={[
          <div
            onClick={() => {
              setModalIsOpen(true);
            }}
          >
            <ToolbarButton key="menu" icon="ion-ios-menu" />
          </div>,
        ]}
      />

      <div className="message-list-container">{result}</div>

      <Compose getText={getText} MY_USER_ID={MY_USER_ID} MY_NAME={MY_NAME} />

      <ReactModal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        className={`modal ${modalIsOpen ? "open" : ""}`}
        overlayClassName={`overlay ${modalIsOpen ? "open" : ""}`}
      >
        <Toolbar
          title={"메뉴"}
          rightItems={[
            <div
              onClick={() => {
                if (window.confirm(`이 채팅방을 나가시겠습니까?`)) {
                  setModalIsOpen(false);
                  outRoom();
                  // setReviewIsOpen(true);
                }
              }}
            >
              <ToolbarButton key="exit" icon="ion-ios-exit" />
            </div>,
          ]}
        />

        <div className="modal-content">
          <div className="room-info">
            <div>채팅방 정보: {state.name}</div>
          </div>
          <div className="appointment-info">
            <div>
              약속 날짜:
              <input type="date" />
            </div>
            <div>
              약속 장소:
              <input type="text" />
            </div>
            <div>
              약속 이름:
              <input type="text" />
            </div>
          </div>
          <div className="button-group">
            <Button className="appointment-button">약속 잡기</Button>
            <Button className="invite-button" onClick={handleShowFriends}>
              친구 초대
            </Button>
          </div>
          {visible && renderFriendsList()}
        </div>
      </ReactModal>
      <ReactModal
        isOpen={reviewIsOpen}
        onRequestClose={() => setReviewIsOpen(false)}
        className={`modal ${reviewIsOpen ? "open" : ""}`}
        overlayClassName={`overlay ${reviewIsOpen ? "open" : ""}`}
      >
        <Review />
      </ReactModal>
    </div>
  );
}
