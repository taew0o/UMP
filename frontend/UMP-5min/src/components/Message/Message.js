import React, { useEffect, useState } from "react";
import moment from "moment";
import "./Message.css";
import axios from "axios";
import { Button, Modal, Tooltip } from "antd";
import { Cell, Legend, Pie, PieChart } from "recharts";

export default function Message(props) {
  const {
    data,
    isMine,
    startsSequence,
    endsSequence,
    showTimestamp,
    senderName, // Add the sender's name as a prop
    isServer,
    prevBySameAuthor,
  } = props;

  const COLORS = ["#4caf50", "#f44336", "#ff9800"];

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [backgroundColor, setBackgroundColor] = useState("");

  const [attendanceData, setAttendance] = useState([]);
  const [content, setContent] = useState();
  const [appointment, setAppointment] = useState();

  useEffect(() => {
    // console.log(
    //   [
    //     "message",
    //     `${isMine ? "mine" : ""}`,
    //     `${isServer ? "server" : ""}`,
    //     `${startsSequence ? "start" : ""}`,
    //     `${endsSequence ? "end" : ""}`,
    //   ].join(" ")
    // );
    // console.log(startsSequence, endsSequence, prevBySameAuthor);
    let storedColor = localStorage.getItem(senderName);
    if (!storedColor) {
      storedColor = getRandomColor();
      localStorage.setItem(senderName, storedColor);
    }
    setBackgroundColor(storedColor);
  }, []);

  useEffect(() => {
    // console.log("currentUser~~~~~~~", currentUser);
    if (appointment) {
      setAttendance([
        { name: "참석", value: appointment.numAttend },
        { name: "불참", value: appointment.numNotAttend },
        { name: "지각", value: appointment.numLate },
      ]);
      setContent(
        `참석 : ${appointment.numAttend}, 불참 : ${appointment.numNotAttend}, 지각 :${appointment.numLate}`
      );
    }
  }, [appointment]);

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
    width: "40px",
    height: "40px",
    borderRadius: "50%",
    color: "black",
    marginRight: "20px",
    backgroundColor: backgroundColor,
    fontSize: "11px",
    cursor: "pointer",
  };

  const renderCustomizedLabel = ({
    cx,
    cy,
    midAngle,
    innerRadius,
    outerRadius,
    percent,
    index,
  }) => {
    const radius = outerRadius + 20;
    const radiusOffset = radius + 30;
    const x = cx + radiusOffset * Math.cos(-midAngle * (Math.PI / 180));
    const y = cy + radiusOffset * Math.sin(-midAngle * (Math.PI / 180));

    // 각 막대 끝에 표시할 레이블
    const labelText =
      index === 0 ? "참석" : index === 1 ? "지각" : index === 2 ? "불참" : "";

    return (
      percent && (
        <text
          x={x}
          y={y}
          fill="black"
          textAnchor="middle"
          dominantBaseline="central"
        >
          {`${(percent * 100).toFixed(0)}%`}
          <tspan x={x} dy={15}>
            {labelText}
          </tspan>{" "}
          {/* 막대 끝에 레이블 표시 */}
        </text>
      )
    );
  };

  const renderLegend = (props) => {
    const { payload } = props;

    return (
      <ul className="pie-chart-legend">
        {payload.map((entry, index) => (
          <span
            className="legend-icon"
            style={{ backgroundColor: COLORS[index % COLORS.length] }}
          ></span>
        ))}
      </ul>
    );
  };

  let friendlyTimestamp = `${senderName} - ${moment().format("LT")}`;
  if (startsSequence || showTimestamp) {
    friendlyTimestamp = `${senderName} - ${moment().format("LLLL")}`;
  }

  const profileClick = () => {
    setModalIsOpen(true);
    axios({
      method: "get",
      url: "/other",
      headers: {
        "Content-Type": `application/json`,
      },
      params: { userId: data.author },
      withCredentials: true,
    })
      .then((response) => {
        setAppointment(response.data.appointmentScore);
      })
      .catch((error) => {
        alert(error.response.data);
      });
  };

  return (
    <div className="container">
      {startsSequence ||
        (showTimestamp && <div className="timestamp">{friendlyTimestamp}</div>)}
      {!prevBySameAuthor && !isServer && startsSequence && !isMine && (
        <>
          <div
            className="conversation-photo"
            style={photoStyle}
            onClick={profileClick}
          >
            <span>{senderName}</span>
          </div>
        </>
      )}
      <div
        className={[
          "message",
          `${isMine ? "mine" : ""}`,
          `${isServer ? "server" : ""}`,
          `${startsSequence ? "start" : ""}`,
          `${endsSequence ? "end" : ""}`,
        ].join(" ")}
      >
        <div className="bubble-container">
          <div className="bubble" title={friendlyTimestamp}>
            {data.message}
          </div>
        </div>
        <Modal
          visible={modalIsOpen}
          onCancel={() => setModalIsOpen(false)}
          footer={null}
        >
          <div className="friend-modal-content">
            <div className="friend-modal-item">
              <div className="friend-photo" style={photoStyle}>
                {senderName}
              </div>
              <div className="friend-info">
                <h1 className="friend-title">
                  {senderName + "(" + data.author + ")"}
                </h1>
              </div>
            </div>
            <div className="attendance-rate-container">
              <h2>약속 참여율: {content}</h2>
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
        </Modal>
      </div>
    </div>
  );
}
