import React, { useEffect, useState } from "react";
import { Layout, Menu } from "antd";
import { AiFillSetting } from "react-icons/ai";
import { BsFillChatFill, BsFillCalendarCheckFill } from "react-icons/bs";
import { FaUserFriends } from "react-icons/fa";
import SettingPage from "../SettingPage/SettingPage";
import FriendPage from "../FriendPage/FriendPage";
import CalendarPage from "../CalendarPage/CalendarPage";
import ChatPage from "../ChatPage/ChatPage";
import { Route, Routes, useNavigate } from "react-router-dom";
import LoginPage from "../LoginPage";
import RegisterPage from "../RegisterPage";

const { Header, Content, Footer, Sider } = Layout;

const App = () => {
  const [selectedPage, setSelectedPage] = useState("1");
  const navigate = useNavigate();
  function movePage(page) {
    navigate("/" + page);
  }

  const renderContent = () => {
    switch (selectedPage) {
      case "1":
        return movePage("");
      case "2":
        return movePage("friend");
      case "3":
        return movePage("calendar");
      case "4":
        return movePage("setting");
      default:
        return;
    }
  };

  useEffect(() => {
    renderContent();
  }, [selectedPage]);

  return (
    <div className="App">
      <Routes>
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />
      </Routes>
      {/* 레이아웃, 사이드 툴바, 메뉴 */}
      <Layout hasSider>
        <Sider style={{ height: "100vh", position: "fixed", left: 0 }}>
          <div
            style={{
              height: 0,
              margin: 16,
              background: "rgba(255, 255, 255, 0.2)",
            }}
          />
          <Menu
            theme="dark"
            mode="inline"
            defaultSelectedKeys={["1"]}
            onSelect={({ key }) => setSelectedPage(key)}
          >
            <Menu.Item key="1" icon={<BsFillChatFill />}>
              채팅
            </Menu.Item>
            <Menu.Item key="2" icon={<FaUserFriends />}>
              친구
            </Menu.Item>
            <Menu.Item key="3" icon={<BsFillCalendarCheckFill />}>
              캘린더
            </Menu.Item>
            <Menu.Item key="4" icon={<AiFillSetting />}>
              설정
            </Menu.Item>
          </Menu>
        </Sider>
        <Layout
          className="site-layout"
          style={{
            marginLeft: 200,
          }}
        >
          <Header />
          <Content>
            <div
              style={{
                margin: "24px 16px 0",
                overflow: "initial",
                padding: 24,
                textAlign: "center",
                minHeight: "280px",
              }}
            >
              {/* {renderContent()} */}
              {/* 경로 지정 */}
              <Routes>
                <Route path="/" element={<ChatPage />} />

                <Route path="/friend" element={<FriendPage />} />
                <Route path="/calendar" element={<CalendarPage />} />
                <Route path="/setting" element={<SettingPage />} />
              </Routes>
            </div>
          </Content>
          <Footer style={{ textAlign: "center" }}>
            Ant Design ©2023 Created by Ant UED
          </Footer>
        </Layout>
      </Layout>
    </div>
  );
};

export default App;
