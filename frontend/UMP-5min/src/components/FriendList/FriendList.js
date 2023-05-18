import React, { useEffect, useState } from "react";
import shave from "shave";
import "./FriendList.css";
import { Button } from "antd";
import Modal from "react-modal";

export default function FriendList(props) {
  //   useEffect(() => {
  //     shave(".friend-snippet", 20);
  //   });

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

  const { photo, name, text } = props.data;
  const [modalIsOpen, setModalIsOpen] = useState(false);

  return (
    <>
      <div className="friend-list-item" onClick={() => setModalIsOpen(true)}>
        <img className="friend-photo" src={photo} alt="friend" />
        <div className="friend-info">
          <h1 className="friend-title">{name}</h1>
          <p className="friend-snippet">{text}</p>
        </div>
      </div>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        style={customStyles}
      >
        <div className="friend-list-item">
          <img className="friend-photo" src={photo} alt="friend" />
          <div className="friend-info">
            <h1 className="friend-title">{name}</h1>
          </div>
        </div>
        <div className="friend-snippet">{text}</div>
      </Modal>
    </>
  );
}
