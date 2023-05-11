import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import RegisterPage from "../RegisterPage";
import LoginPage from "../LoginPage";
import MainPage from "../MainPage";
import FriendPage from "../FriendPage";

const App = () => {
  const [isLogin, setLogin] = useState(true);
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={isLogin ? <MainPage /> : <LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/friend" element={<FriendPage />} />
          {/* <Messenger /> */}
          {/* <RegisterPage /> */}
          {/* <LoginPage /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
};
export default App;
