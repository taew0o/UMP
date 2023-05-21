import React, { useState, useEffect } from 'react';
import './CalendarPage.css';
import { Badge, Calendar, Modal, Input, Button, List } from 'antd';


const CalendarPage = () => {
  const [events, setEvents] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [editEventIndex, setEditEventIndex] = useState(null);
  const [inputValue, setInputValue] = useState('');

  const handleDateClick = (value) => {
    setSelectedDate(value);
    setModalVisible(true);
  };

  const handleModalCancel = () => {
    setSelectedDate(null);
    setModalVisible(false);
    setEditEventIndex(null);
  };

  const dateCellRender = (value) => {
    const dateEvents = events.filter(event => event.date.isSame(value, 'day'));

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
    return events.filter(event => event.date.isSame(selectedDate, 'day'));
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
        dateCellRender={dateCellRender}
        onSelect={handleDateClick}
      />
      <Modal
        title={`event ${selectedDate ? selectedDate.format('YYYY-MM-DD') : ''}`}
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
              <Button onClick={() => {
                setEditEventIndex(index);
                setInputValue(events[index].content);
              }}>Edit</Button>
              <Button onClick={() => handleDeleteEvent(index)}>Delete</Button>
            </List.Item>
          )}
        />
      </Modal>
    </div>
  );
};

export default CalendarPage;
