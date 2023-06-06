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
    console.log(events);
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
        console.log("----------------", response);
        const e = response.data
          .filter((value) => value.time !== "")
          .map((value) => {
            const dateOnly = value.time.split(" ")[0];
            // console.log(tempDate);
            // const dateOnly = new Date(moment(tempDate));
            // console.log(new Date(dateOnly));
            const content = (
              <div>
                약속 이름: {value.chattingRoomName}
                <br />
                약속 장소: {value.location}
                <br />
                약속 시간: {value.time}
              </div>
            );
            const newEvent = { content: content, date: dateOnly };
            return newEvent;
          });

        setEvents(e);
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data);
      });
  };

  const dateCellRender = (value) => {
    console.log(value.format("YYYY-MM-DD"));
    const dateEvents = events.filter((event) => {
      return event.date === value.format("YYYY-MM-DD");
      // event.date.isSame(value, "day");
      // console.log(event.date, value);
    });
    console.log(dateEvents);

    return (
      <ul className="event-list">
        {dateEvents.map((elem, index) => (
          <li key={index}>
            <Badge status="success" text={elem.content} />
          </li>
        ))}
      </ul>
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
    console.log(events);
    console.log(selectedDate);
    // return events.filter((event) => event.date.isSame(selectedDate, "day"));
  };

  // 이벤트 수정 시 inputValue를 이벤트 내용으로 설정
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
        title={`event ${selectedDate ? selectedDate.format("YYYY-MM-DD") : ""}`}
        visible={modalVisible}
        onCancel={handleModalCancel}
        footer={null}
      >
        <Input
          placeholder="Enter event"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          onPressEnter={() => handleOk()}
        />
        <Button onClick={handleOk}>Save</Button>
        <List
          dataSource={getDateEvents()}
          renderItem={(item, index) => (
            <List.Item>
              <span>{item.content}</span>
              <Button
                onClick={() => {
                  setEditEventIndex(index);
                  setInputValue(events[index].content);
                }}
              >
                Edit
              </Button>
              <Button onClick={() => handleDeleteEvent(index)}>Delete</Button>
            </List.Item>
          )}
        />
      </Modal>
    </div>
  );
};

export default CalendarPage;
