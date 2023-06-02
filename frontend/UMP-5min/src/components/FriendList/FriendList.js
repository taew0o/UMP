import React, { useState, useEffect } from "react";
import "./FriendList.css";
import { Button, Modal } from "antd";
import Chart from "chart.js/auto";

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

  useEffect(() => {
    if (appointmentScore && id) {
      const labels = ["참석", "불참", "지각"];
      const data = [
        appointmentScore.numAttend,
        appointmentScore.numNotAttend,
        appointmentScore.numLate,
      ];

      const chartConfig = {
        type: "bar",
        data: {
          labels: labels,
          datasets: [
            {
              data: data,
              backgroundColor: [
                "rgba(75, 192, 192, 0.6)",
                "rgba(255, 99, 132, 0.6)",
                "rgba(255, 205, 86, 0.6)",
              ],
              borderWidth: 1,
            },
          ],
        },
        options: {
          scales: {
            y: {
              beginAtZero: true,
              max: Math.max(...data) + 1,
              precision: 0,
              stepSize: 1,
            },
          },
        },
      };

      const canvas = document.getElementById(`chart-${id}`);
      if (canvas) {
        new Chart(canvas, chartConfig);
      }
    }
  }, [appointmentScore, id]);

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
    backgroundColor: backgroundColor,
  };

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
          <div className="modal-button-container">
            <Button onClick={handleDeleteClick}>친구 삭제</Button>
            <Button onClick={handleBlockClick}>친구 차단</Button>
          </div>
          <div className="attendance-rate-container">
            <h2>약속 참여율:</h2>
            <div className="attendance-rate-box">
              <canvas id={`chart-${id}`} width="400" height="200"></canvas>
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
