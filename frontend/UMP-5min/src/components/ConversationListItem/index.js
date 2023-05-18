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
    navigate(`/room`, { state: { name, photo, text } });
  };
  const { photo, name, text } = props.data;

  return (
    <div className="conversation-list-item" onClick={clickHandler}>
      <img className="conversation-photo" src={photo} alt="conversation" />
      <div className="conversation-info">
        <h1 className="conversation-title">{name}</h1>
        <p className="conversation-snippet">{text}</p>
      </div>
    </div>
  );
};
export default ConversationListItem;
