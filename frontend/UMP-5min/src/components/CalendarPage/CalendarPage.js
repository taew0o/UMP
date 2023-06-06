import React, { useState, useEffect } from "react";
import "./CalendarPage.css";
import { Badge, Calendar, Modal, Input, Button, List } from "antd";
import axios from "axios";
import moment from "moment";

const CalendarPage = () => {
  const [events, setEvents] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [editEventIndex, setEditEventIndex] = useState(null);
  const [inputValue, setInputValue] = useState("");

  const [newEvent, setNewEvent] = useState([]);

  const handleDateClick = (value) => {
    setSelectedDate(value);
    setModalVisible(true);
  };

  useEffect(() => {
    addAppointment();
  }, []);

  const handleModalCancel = () => {
    setSelectedDate(null);
    setModalVisible(false);
    setEditEventIndex(null);
  };

  const addAppointment = () => {
    axios({
      method: "get",
      url: "/appointment",
      headers: {
        "Content-Type": `application/json`,
      },
      withCredentials: true,
    })
      .then((response) => {
        const e = response.data
          .filter((value) => value.time !== "")
          .map((value) => {
            const dateOnly = value.time.split(" ")[0];
            const content = (
              <div className="content">
                약속 이름: {value.chattingRoomName}
                <br />
                약속 장소: {value.location}
                <br />
                약속 시간: {value.time.split(" ")[1]}
              </div>
            );
            const newEvent = { content: content, date: dateOnly };
            return newEvent;
          });

        setEvents(e);
      })
      .catch((error) => {
        alert(error.response.data);
      });
  };

  const dateCellRender = (value) => {
    const dateEvents = events.filter((event) => {
      return event.date === value.format("YYYY-MM-DD");
    });

    return (
      <div className="event-list">
        {dateEvents.map((elem, index) => (
          // <li key={index}>
          <Badge
            className="badge"
            status="success"
            text={elem.content}
            overflowCount={10}
          />
          // </li>
        ))}
      </div>
    );
  };

  const handleOk = () => {
    if (inputValue && inputValue.trim()) {
      if (editEventIndex === null) {
        // 새 이벤트 추가
        const newEvent = { content: inputValue.trim(), date: selectedDate };
        const newEvents = [...events, newEvent];
        setEvents(newEvents);
      } else {
        // 기존 이벤트 수정
        const newEvents = events.map((event, index) => {
          if (index === editEventIndex) {
            return { content: inputValue.trim(), date: selectedDate };
          } else {
            return event;
          }
        });
        setEvents(newEvents);
        setEditEventIndex(null);
      }

      setInputValue("");
    }
    handleModalCancel();
  };

  const handleDeleteEvent = (index) => {
    const newEvents = events.filter((_, idx) => idx !== index);
    setEvents(newEvents);
  };

  const getDateEvents = () => {
    if (selectedDate) {
      return events.filter(
        (event) => event.date === selectedDate.format("YYYY-MM-DD")
      );
    }
    return [];
  };

  useEffect(() => {
    if (editEventIndex !== null) {
      setInputValue(events[editEventIndex].content);
    }
  }, [editEventIndex]);

  return (
    <div>
      <Calendar
        className="my-calendar"
        cellRender={dateCellRender}
        onSelect={handleDateClick}
      />
      <Modal
        title={`일정 ${selectedDate ? selectedDate.format("YYYY-MM-DD") : ""}`}
        visible={modalVisible}
        onCancel={handleModalCancel}
        footer={null}
      >
        <List
          dataSource={getDateEvents()}
          renderItem={(item, index) => (
            <List.Item>
              <span className="event-text">{item.content}</span>
            </List.Item>
          )}
        />
      </Modal>
    </div>
  );
};

export default CalendarPage;
