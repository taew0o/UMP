import React from "react"
import { Route, Routes } from "react-router-dom";
import RegisterPage from "../RegisterPage";
import LoginPage from "../LoginPage";
import Home from "../Home";

const Router = () => {
  return (
    <Routes>
      {/* 이중 라우팅 때문에 애스터리스크 필요 */}
      <Route path="/*" element={<Home />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  );
};
export default Router;
