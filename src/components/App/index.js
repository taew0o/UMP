import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Messenger from "../Messenger";
import RegisterPage from "../RegisterPage";
import LoginPage from "../LoginPage";

const App = () => {
  const [isLogin, setLogin] = useState(false);
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={isLogin ? <Messenger /> : <LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          {/* <Messenger /> */}
          {/* <RegisterPage /> */}
          {/* <LoginPage /> */}
        </Routes>
      </BrowserRouter>
    </div>
  );
};
export default App;
