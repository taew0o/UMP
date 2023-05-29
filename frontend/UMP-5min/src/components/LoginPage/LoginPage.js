import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Checkbox, Form, Input } from "antd";
import axios from "axios";
import cookie from "react-cookies";

const LoginPage = () => {
  const navigate = useNavigate();
  const toHome = () => {
    navigate("/");
  };
  const toRegister = () => {
    navigate("/register");
  };
  const onFinish = (values) => {
    console.log("Received values of form: ", values);
    console.log(values.id, values.password);
    axios({
      method: "post",
      url: "http://localhost:8080/login",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        id: values.id,
        password: values.password,
      },
      proxy: "http://localhost:8080/login",
    })
      .then((response) => {
        console.log("--------------------", response);
        toHome();
      })
      .catch(function (error) {
        console.log(error);
      });
    const expires = new Date();
    expires.setMinutes(expires.getMinutes() + 60);
    cookie.save("JSESSIONID", values.id, {
      path: "/",
      expires,
      // secure : true,
      // httpOnly : true
    });
  };
  return (
    <Form
      name="normal_login"
      className="login-form"
      initialValues={{
        remember: true,
      }}
      onFinish={onFinish}
    >
      <Form.Item
        name="id"
        rules={[
          {
            required: true,
            message: "Please input your Username!",
          },
        ]}
      >
        <Input
          prefix={<UserOutlined className="site-form-item-icon" />}
          placeholder="아이디"
        />
      </Form.Item>
      <Form.Item
        name="password"
        rules={[
          {
            required: true,
            message: "Please input your Password!",
          },
        ]}
      >
        <Input
          prefix={<LockOutlined className="site-form-item-icon" />}
          type="password"
          placeholder="비밀번호"
        />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" className="login-form-button">
          로그인
        </Button>
        <Button
          type="primary"
          htmlType="submit"
          className="register-form-button"
          onClick={toRegister}
        >
          회원가입
        </Button>
      </Form.Item>
    </Form>
  );
};

export default LoginPage;
