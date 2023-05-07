import React, { useState } from "react";
import Messenger from "../Messenger";
import RegisterPage from "../RegisterPage";
import LoginPage from "../LoginPage";

export default function App() {
  // const [isLogin, setLogin] = useState(false);
  return (
    <div className="App">
      {/* {isLogin ? <Messenger /> : <LoginPage />} */}
      {/* <Messenger /> */}
      {/* <RegisterPage /> */}
      <LoginPage />
    </div>
  );
}
