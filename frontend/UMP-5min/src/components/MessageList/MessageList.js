import React, { useEffect, useState } from "react";
import Compose from "../Compose/Compose";
import Toolbar from "../Toolbar/Toolbar";
import ToolbarButton from "../ToolbarButton/ToolbarButton";
import Message from "../Message/Message";
import moment from "moment";

import "./MessageList.css";
import { useLocation, useParams } from "react-router-dom";
import ReactModal from "react-modal";

const MY_USER_ID = "apple";

export default function MessageList(props) {
  const [messages, setMessages] = useState([]);
  const location = useLocation();
  const { id } = useParams();
  const state = location.state;

  const [result, setResult] = useState([]);
  const [text, setText] = useState();

  const customStyles = {
    content: {
      top: "35%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      height: "50%",
      width: "50%",
      transform: "translate(-40%, -10%)",
    },
  };

  const [modalIsOpen, setModalIsOpen] = useState(false);

  useEffect(() => {
    console.log(text);
    makeMsg();
    renderMessages();

    console.log(messages);
  }, [text]);

  const getText = (prop) => {
    setText(prop);
  };

  const makeMsg = () => {
    if (text) {
      setMessages([...messages, ...text]);
    }
  };

  useEffect(() => {
    renderMessages();
  }, [messages]);
  
  const getMessages = () => {
    var tempMessages = [
      {
        id: 1,
        author: "apple",
        message:
          "Hello world! This is a long message that will hopefully get wrapped by our message bubble component! We will see how well it works.",
        timestamp: new Date().getTime(),
      },
      {
        id: 2,
        author: "orange",
        message:
          "It looks like it wraps exactly as it is supposed to. Lets see what a reply looks like!",
        timestamp: new Date().getTime(),
      },
      {
        id: 3,
        author: "orange",
        message:
          "Hello world! This is a long message that will hopefully get wrapped by our message bubble component! We will see how well it works.",
        timestamp: new Date().getTime(),
      },
      {
        id: 4,
        author: "apple",
        message:
          "It looks like it wraps exactly as it is supposed to. Lets see what a reply looks like!",
        timestamp: new Date().getTime(),
      },
      {
        id: 5,
        author: "apple",
        message:
          "Hello world! This is a long message that will hopefully get wrapped by our message bubble component! We will see how well it works.",
        timestamp: new Date().getTime(),
      },
      {
        id: 6,
        author: "apple",
        message:
          "It looks like it wraps exactly as it is supposed to. Lets see what a reply looks like!",
        timestamp: new Date().getTime(),
      },
      {
        id: 7,
        author: "orange",
        message:
          "Hello world! This is a long message that will hopefully get wrapped by our message bubble component! We will see how well it works.",
        timestamp: new Date().getTime(),
      },
      {
        id: 8,
        author: "orange",
        message:
          "It looks like it wraps exactly as it is supposed to. Lets see what a reply looks like!",
        timestamp: new Date().getTime(),
      },
      {
        id: 9,
        author: "apple",
        message:
          "Hello world! This is a long message that will hopefully get wrapped by our message bubble component! We will see how well it works.",
        timestamp: new Date().getTime(),
      },
      {
        id: 10,
        author: "orange",
        message:
          "It looks like it wraps exactly as it is supposed to. Lets see what a reply looks like!",
        timestamp: new Date().getTime(),
      },
    ];
    setMessages([...messages, ...tempMessages]);
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
    console.log("?????????????");
    setResult(tempMessages);
  };
  
  return (
    <div className="message-list">
      <Toolbar
        title={"test"}
        rightItems={[
          <div
            onClick={() => {
              setModalIsOpen(true);
            }}
          >
            <ToolbarButton key="exit" icon="ion-ios-exit" />
          </div>,
          <ToolbarButton
            key="info"
            icon="ion-ios-information-circle-outline"
          />,
        ]}
      />

      <div className="message-list-container">{result}</div>

      <Compose
        messages={messages}
        getText={getText}
        setMessages={setMessages}
      />

      {/* 채팅 설정 모달 */}
      <ReactModal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        style={customStyles}
      >
        <div>
          <h2>채팅 설정</h2>
          <div>
            <div>채팅 정보: {id}</div>
            <button>친구 초대</button>
          </div>
          <div>
            <label>
              약속 날짜:
              <input type="date" />
            </label>
            <label>
              약속 장소:
              <input type="text" />
            </label>
            <label>
              약속 이름:
              <input type="text" />
            </label>
          </div>
          <div>
            <button onClick={() => setModalIsOpen(false)}>나가기</button>
            <button>약속 잡기</button>
          </div>
        </div>
      </ReactModal>
    </div>
  );
}  