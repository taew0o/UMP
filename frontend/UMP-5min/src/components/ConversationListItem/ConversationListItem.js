import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import shave from "shave";
import { BiTimeFive } from "react-icons/bi";
import "./ConversationListItem.css";

const ConversationListItem = (props) => {
  const navigate = useNavigate();
  const [backgroundColor, setBackgroundColor] = useState("");

  useEffect(() => {
    shave(".conversation-snippet", 20);
  });

  useEffect(() => {
    let storedColor = localStorage.getItem(name);
    if (!storedColor) {
      storedColor = getRandomColor();
      localStorage.setItem(name, storedColor);
    }
    setBackgroundColor(storedColor);
  }, []);

  const clickHandler = () => {
    console.log(name);
    navigate(`/room/${id}`, {
      state: { id, name, member, createTime, isAppoint, date, time },
    });
  };
  const { id, member, name, createTime, isAppoint, date, time } = props.data;

  const getRandomColor = () => {
    const colors = [
      "#FF5B5B",
      "#FFB36B",
      "#FFE66D",
      "#9ED763",
      "#3EBDFF",
      "#A36CFF",
      "#FF7EB1",
      "#ADADAD",
    ];
    const randomIndex = Math.floor(Math.random() * colors.length);
    return colors[randomIndex];
  };

  const Style = {
    backgroundColor: backgroundColor,
  };

  return (
    <div className="conversation-list-item" onClick={clickHandler}>
      <div className="conversation-photo" style={Style}>
        <span>{name}</span>
      </div>
      <div className="conversation-time">
        <div className="conversation-info">
          <h1 className="conversation-title">{name}</h1>
          {/* <p className="conversation-snippet">{text}</p> */}
        </div>

        {isAppoint ? (
          <div className="conversation-appoint">
            <BiTimeFive
              style={{ display: "flex", fontSize: "24px", color: "blue" }}
            />
          </div>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
};

export default ConversationListItem;
