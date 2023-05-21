import React, { useState } from 'react';
import './CalendarPage.css';
import { Badge, Calendar, Modal, Input, Button, List } from 'antd';

const CalendarPage = () => {
  const [events, setEvents] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [editEventIndex, setEditEventIndex] = useState(null);
  const [inputValue, setInputValue] = useState(''); // 추가: inputValue 상태

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

  const handleOk = (content) => {
    if (content && content.trim()) {
      if (editEventIndex === null) {
        // 새 이벤트 추가
        const newEvent = { content: content.trim(), date: selectedDate };
        const newEvents = [...events, newEvent];
        setEvents(newEvents);
      } else {
        // 기존 이벤트 수정
        const newEvents = events.map((event, index) => {
          if (index === editEventIndex) {
            return { content: content.trim(), date: selectedDate };
          } else {
            return event;
          }
        });
        setEvents(newEvents);
        setEditEventIndex(null);
      }

      setInputValue(''); // 추가: 입력창 비우기
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

  return (
    <div>
      <Calendar
        className="my-calendar"
        dateCellRender={dateCellRender}
        onSelect={handleDateClick}
      />
      <Modal
        title={`약속 ${selectedDate ? selectedDate.format('YYYY-MM-DD') : ''}`}
        visible={modalVisible}
        onCancel={handleModalCancel}
        footer={null}
      >
        <Input
          placeholder="Enter event"
          value={inputValue} // 추가: inputValue 전달
          onChange={(e) => setInputValue(e.target.value)} // 추가: 입력값에 따라 inputValue 업데이트
          onPressEnter={(e) => handleOk(e.target.value)}
        />
        <Button onClick={() => handleOk()}>Save</Button>
        <List
          dataSource={getDateEvents()}
          renderItem={(item, index) => (
            <List.Item>
              <span>{item.content}</span>
              <Button onClick={() => { setEditEventIndex(index); }}>Edit</Button>
              <Button onClick={() => handleDeleteEvent(index)}>Delete</Button>
            </List.Item>
          )}
        />
      </Modal>
    </div>
  );
};

export default CalendarPage;
