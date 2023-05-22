import React, { useState } from "react";
import "./FriendList.css";
import { Button, Modal } from "antd";

export default function FriendList(props) {
  const { photo, name, text } = props.data;
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [confirmModalIsOpen, setConfirmModalIsOpen] = useState(false);
  const [confirmModalTitle, setConfirmModalTitle] = useState("");

  const handleDeleteClick = () => {
    setConfirmModalTitle("친구 삭제");
    setConfirmModalIsOpen(true);
  };

  const handleBlockClick = () => {
    setConfirmModalTitle("친구 차단");
    setConfirmModalIsOpen(true);
  };

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
    <>
      <div className="friend-list-item" onClick={() => setModalIsOpen(true)}>
        <div className="friend-photo" style={photoStyle}>
          {photo}
        </div>
        <div className="friend-info">
          <h1 className="friend-title">{name}</h1>
          <p className="friend-snippet">{text}</p>
        </div>
      </div>
      <Modal
        visible={modalIsOpen}
        onCancel={() => setModalIsOpen(false)}
        footer={null}
      >
        <div className="friend-modal-content">
          <div className="friend-list-item">
            <img className="friend-photo" src={photo} alt="friend" />
            <div className="friend-info">
              <h1 className="friend-title">{name}</h1>
            </div>
          </div>
          <div className="modal-button-container">
            <Button onClick={handleDeleteClick}>친구 삭제</Button>
            <Button onClick={handleBlockClick}>친구 차단</Button>
          </div>
          <div className="attendance-rate-container">
            <h2>약속 참여율:</h2>
            <div className="attendance-rate-box">
              {/* 약속 참여율 정보를 출력하는 코드가 들어갈 위치입니다. */}
            </div>
          </div>
        </div>
      </Modal>
      <Modal
        title={confirmModalTitle}
        visible={confirmModalIsOpen}
        onCancel={() => setConfirmModalIsOpen(false)}
        onOk={() => console.log(`${confirmModalTitle} 완료`)}
        okButtonProps={{ style: { float: "left" } }}
        okText="예"
        cancelText="아니오"
      >
        {`${
          confirmModalTitle === "친구 삭제"
            ? `${name}을(를) 삭제`
            : `${name}을(를) 차단`
        }하시겠습니까?`}
      </Modal>
    </>
  );
}
