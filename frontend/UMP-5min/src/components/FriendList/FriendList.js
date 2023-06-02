import React, { useState, useEffect } from "react";
import "./FriendList.css";
import { Button, Modal } from "antd";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";


export default function FriendList(props) {
  const { id, name, text, appointmentScore } = props.data;
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [confirmModalIsOpen, setConfirmModalIsOpen] = useState(false);
  const [confirmModalTitle, setConfirmModalTitle] = useState("");
  const [backgroundColor, setBackgroundColor] = useState("");

  useEffect(() => {
    let storedColor = localStorage.getItem(name);
    if (!storedColor) {
      storedColor = getRandomColor();
      localStorage.setItem(name, storedColor);
    }
    setBackgroundColor(storedColor);
  }, []);
  
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
      "#FFBB",
      "#FFE66D",
      "#9ED763",
      "#3EBDFF",
      "#A36CFF",
      "#FF7EB1",
      "#ADADAD",
    ];
    const randomIndex = Math(Math.random() * colors.length);
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
    backgroundColor: backgroundColor,
  };

  const attendanceData = [
    { name: "참석", value: 10 },
    { name: "불참", value: 4 },
    { name: "지각", value: 2 },
  ];

  const COLORS = ["#4caf50", "#f44336", "#ff9800"];

  return (
    <>
      <div className="friend-list-item" onClick={() => setModalIsOpen(true)}>
        <div className="friend-photo" style={photoStyle}>
          {id}
        </div>
        <div className="friend-info">
          <div className="friend-title">{name}</div>
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
            <div className="friend-photo" style={photoStyle}>
              {id}
            </div>
            <div className="friend-info">
              <h1 className="friend-title">{name}</h1>
            </div>
          </div>
          <div className="attendance-rate-container">
              <h2>약속 참여율:</h2>
              <PieChart width={200} height={200}>
                <Pie
                  data={attendanceData}
                  cx={100}
                  cy={100}
                  outerRadius={80}
                  dataKey="value"
                  label
                >
                  {attendanceData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend verticalAlign="middle" height="36" align="right" />
              </PieChart>
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
