import React, { useCallback, useEffect, useRef, useState } from "react";
import Compose from "../Compose/Compose";
import Toolbar from "../Toolbar/Toolbar";
import ToolbarButton from "../ToolbarButton/ToolbarButton";
import Message from "../Message/Message";
import moment from "moment";

import "./MessageList.css";
import { useLocation, useParams } from "react-router-dom";
import ReactModal from "react-modal";
import Review from "../Review/Review";
import { Button } from "antd";
import axios from "axios";

export default function MessageList({ props }) {
  const MY_USER_ID = props.id;

  const [messages, setMessages] = useState([]);
  const location = useLocation();
  const { id } = useParams();
  const state = location.state;

  const [result, setResult] = useState([]);
  const [text, setText] = useState();

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [reviewIsOpen, setReviewIsOpen] = useState(false);

  const [socketConnected, setSocketConnected] = useState(false);
  const [sendMsg, setSendMsg] = useState(false);
  const [items, setItems] = useState([]);

  const webSocketUrl = "ws://localhost:8080/websocket?roomId=" + id;
  const ws = useRef(null);

  useEffect(() => {
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
        };
        setMessages((prevMessages) => [...prevMessages, tempMsg]);
        // renderMessages();
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
        })
      );
      console.log("메시지 보낸다");

      setSendMsg(true);
    }
    console.log("message!!!!!!!", messages);
    // renderMessages();
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
      // send();
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
            author: value.user.id,
            message: value.textMsg,
            timestamp: value.sendTime,
          };
          setMessages((prevMessages) => [...prevMessages, tempMsg]);
        });
      })
      .catch((error) => {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
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

      tempMessages.push(
        <Message
          key={i}
          isMine={isMine}
          startsSequence={startsSequence}
          endsSequence={endsSequence}
          showTimestamp={showTimestamp}
          data={current}
        />
      );

      // Proceed to the next message.
      i += 1;
    }
    setResult(tempMessages);
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

      <Compose getText={getText} MY_USER_ID={MY_USER_ID} />

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
                  setReviewIsOpen(true);
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
            <Button className="invite-button">친구 초대</Button>
          </div>
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
