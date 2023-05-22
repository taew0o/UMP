import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import shave from "shave";
import "./ConversationListItem.css";

const ConversationListItem = (props) => {
  const navigate = useNavigate();
  useEffect(() => {
    shave(".conversation-snippet", 20);
  });
  const clickHandler = () => {
    console.log(name);
    navigate(`/room/${id}`, { state: { id, name, photo, text } });
  };
  const { id, photo, name, text } = props.data;

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

  const photoStyle = {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    width: "50px",
    height: "50px",
    borderRadius: "50%",
    color: "black",
    marginRight: "20px",
    backgroundColor: getRandomColor(),
  };

  return (
    <div className="conversation-list-item" onClick={clickHandler}>
      <div className="conversation-photo" style={photoStyle}>
        {photo}
      </div>
      <div className="conversation-info">
        <h1 className="conversation-title">{name}</h1>
        <p className="conversation-snippet">{text}</p>
      </div>
    </div>
  );
};

export default ConversationListItem;
