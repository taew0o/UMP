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

const { Header, Content, Footer, Sider } = Layout;

const Home = () => {
  const [selectedPage, setSelectedPage] = useState();
  const [myData, setMyData] = useState();
  const navigate = useNavigate();
  function movePage(page) {
    navigate("/" + page);
  }

  const isLogin = true; // 로그인 됐는지는 서버 연결하면 제대로 바꿈

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
    const myId = cookie.load("userId");
    if (!myId) return movePage("login");
    console.log("내 아디 :", myId);
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
      })
      .catch(function (error) {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
      });
  }, [selectedPage]);

  return (
    <>
      {isLogin ? (
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
                {/* 경로 지정 */}
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
            </Content>
            <Footer style={{ textAlign: "center" }}>
              Ant Design ©2023 Created by Ant UED
            </Footer>
          </Layout>
        </Layout>
      ) : (
        movePage("login")
      )}
    </>
  );
};
export default Home;
