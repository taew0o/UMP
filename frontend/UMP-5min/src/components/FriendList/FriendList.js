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

  const COLORS = ["#4caf50", "#f44336", "#ff9800"];

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

  const attendanceData = [
    { name: "참석", value: 10 },
    { name: "불참", value: 4 },
    { name: "지각", value: 2 },
  ];
  const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
    const radius = outerRadius + 20;
    const radiusOffset = radius + 30;
    const x = cx + radiusOffset * Math.cos(-midAngle * (Math.PI / 180));
    const y = cy + radiusOffset * Math.sin(-midAngle * (Math.PI / 180));
  
    // 각 막대 끝에 표시할 레이블
    const labelText = index === 0 ? "참석" : index === 1 ? "지각" : index === 2 ? "불참" : "";
  
    return (
      <text
        x={x}
        y={y}
        fill="black"
        textAnchor="middle"
        dominantBaseline="central"
      >
        {`${(percent * 100).toFixed(0)}%`}
        <tspan x={x} dy={15}>{labelText}</tspan> {/* 막대 끝에 레이블 표시 */}
      </text>
    );
  };
  
  
  

  const renderLegend = (props) => {
    const { payload } = props;

    return (
      <ul className="pie-chart-legend">
        {payload.map((entry, index) => (
          <li key={`legend-${index}`}>
            <span className="legend-icon" style={{ backgroundColor: COLORS[index % COLORS.length] }}></span>
            <span className="legend-label">{attendanceData[index].name}: {attendanceData[index].value}</span>
          </li>
        ))}
      </ul>
    );
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
          <div className="attendance-rate-container">
            <h2>약속 참여율:</h2>
            <PieChart width={400} height={400}>
              <Pie
                data={attendanceData}
                cx={200}
                cy={200}
                outerRadius={80}
                dataKey="value"
                label={renderCustomizedLabel}
              >
                {attendanceData.map((entry, index) => (
                  <Cell
                    key={`cell-${index}`}
                    fill={COLORS[index % COLORS.length]}
                  />
                ))}
              </Pie>
              <Tooltip />
              <Legend
                verticalAlign="middle"
                height={36}
                align="right"
                content={renderLegend}
              />
            </PieChart>
          </div>
        </div>
        <div className="friend-modal-buttons" style={{ marginTop: "10px", display: "flex", justifyContent: "flex-end" }}>
          <Button type="primary" onClick={handleDeleteClick} style={{ marginRight: "10px" }}>
            삭제
          </Button>
          <Button type="primary" onClick={handleBlockClick}>
            차단
          </Button>
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