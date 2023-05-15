import React, { useState } from 'react';
import { Layout, Menu } from 'antd';
import {
  AppstoreOutlined,
  BarChartOutlined,
  CloudOutlined,
  ShopOutlined,
  TeamOutlined,
  UploadOutlined,
  UserOutlined,
  VideoCameraOutlined,
} from '@ant-design/icons';
import SettingPage from '../SettingPage/SettingPage';
import FriendPage from '../FriendPage/FriendPage';
import CalendarPage from '../CalendarPage/CalendarPage';
import ChatPage from '../ChatPage/ChatPage';


const { Header, Content, Footer, Sider } = Layout;

const App = () => {
  const [selectedPage, setSelectedPage] = useState('1');

  const renderContent = () => {
    switch (selectedPage) {
      case '1':
        return <SettingPage />;
      case '2':
        return <FriendPage />;
      case '3':
        return <CalendarPage />;
      case '4':
        return <ChatPage />;
      default:
        return <SettingPage />;
    }
  };

  return (
    <Layout hasSider>
      <Sider style={{ height: '100vh', position: 'fixed', left: 0 }}>
        <div
          style={{
            height: 32,
            margin: 16,
            background: 'rgba(255, 255, 255, 0.2)',
          }}
        />
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={['1']}
          onSelect={({ key }) => setSelectedPage(key)}
        >
          <Menu.Item key="1" icon={<UserOutlined />}>
            Setting Page
          </Menu.Item>
          <Menu.Item key="2" icon={<VideoCameraOutlined />}>
            Friend Page
          </Menu.Item>
          <Menu.Item key="3" icon={<UploadOutlined />}>
            Calendar Page
          </Menu.Item>
          <Menu.Item key="4" icon={<BarChartOutlined />}>
            Chat Page
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
              margin: '24px 16px 0',
              overflow: 'initial',
              padding: 24,
              textAlign: 'center',
              minHeight: '280px',
            }}
          >
            {renderContent()}
          </div>
        </Content>
        <Footer style={{ textAlign: 'center' }}>Ant Design ©2023 Created by Ant UED</Footer>
      </Layout>
    </Layout>
  );
};

export default App;
