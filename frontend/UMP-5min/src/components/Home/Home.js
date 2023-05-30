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
import MessageList from "../MessageList/MessageList";
import Review from "../Review/Review";
import cookie from "react-cookies";
import axios from "axios";
import useInternetConnection from "../useInternetConnection";
import LoginPage from "../LoginPage/LoginPage";

const { Header, Content, Footer, Sider } = Layout;

function getDefaultSelectedKey() {
  const storedKey = localStorage.getItem("selectedKey");
  return storedKey ? storedKey : "1";
}

const Home = () => {
  const [myData, setMyData] = useState();
  const [selectedPage, setSelectedPageKey] = useState(getDefaultSelectedKey());
  const navigate = useNavigate();
  const isOnline = useInternetConnection();
  const [isLoading, setLoad] = useState(false);

  useEffect(() => {
    if (!isOnline) {
      navigate("/login");
    }
  }, [isOnline, navigate]);

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
    // const myId = cookie.load("userId");
    // if (!myId) return movePage("login");
    // console.log("내 아디 :", myId);
    axios({
      method: "get",
      url: "http://localhost:8080/user",
      headers: {
        "Content-Type": `application/json`,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response.data);
        setMyData(response.data);
        setLoad(true);
      })
      .catch(function (error) {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
      });
  }, []);

  function setSelectedPage(key) {
    setSelectedPageKey(key);
    localStorage.setItem("selectedKey", key);
  }

  useEffect(() => {
    renderContent();
  }, [selectedPage]);

  return (
    <>
      {isOnline ? (
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
              selectedKeys={[getDefaultSelectedKey()]}
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
              {isLoading ? (
                <div
                  style={{
                    margin: "24px 16px 0",
                    overflow: "initial",
                    padding: 24,
                    textAlign: "center",
                    minHeight: "280px",
                  }}
                >
                  <Routes>
                    <Route path="/" exact element={<ChatPage />} />
                    <Route path="/friend" element={<FriendPage />} />
                    <Route path="/calendar" element={<CalendarPage />} />
                    <Route
                      path="/setting"
                      element={<SettingPage props={myData} />}
                    />
                    <Route path="/room/:id" element={<MessageList />} />
                    <Route path="/review/:id" element={<Review />} />
                  </Routes>
                </div>
              ) : (
                <h1 style={{ textAlign: "center" }}>Loading...</h1>
              )}
            </Content>
            <Footer style={{ textAlign: "center" }}>
              Ant Design ©2023 Created by Ant UED
            </Footer>
          </Layout>
        </Layout>
      ) : (
        <LoginPage />
      )}
    </>
  );
};

export default Home;
