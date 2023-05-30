import React, { useEffect, useState } from "react";
import { Button, Col, Form, Input, Row } from "antd";
import axios from "axios";
import cookie from "react-cookies";

const formItemLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 8 },
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 16 },
  },
};

const SettingPage = ({ props }) => {
  const [form] = Form.useForm();

  useEffect(() => {
    console.log(props.name);
    console.log(props.id);
    console.log(props.phone_num);
  }, []);

  const onFinish = (values) => {
    console.log("Received values of form: ", values);
    console.log(document.cookie);
    axios({
      method: "put",
      url: "/user",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        name: values.nickname,
        phone_num: values.phone,
        password: values.password,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("--------------------", response);
        alert(`변경했습니다!`);
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  return (
    <Form
      {...formItemLayout}
      form={form}
      name="setting"
      onFinish={onFinish}
      style={{ maxWidth: 600 }}
      scrollToFirstError
    >
      <Form.Item
        name="nickname"
        label="닉네임"
        initialValue={props.name}
        rules={[
          {
            required: true,
            message: "Please input your nickname!",
            whitespace: true,
          },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name="phone"
        label="전화번호"
        initialValue={props.phone_num}
        rules={[
          {
            required: true,
            message: "Please input your phone number!",
          },
        ]}
      >
        <Input />
        {/* <input defaultValue={myData.} /> */}
      </Form.Item>
      <Form.Item
        name="password"
        label="비밀번호"
        initialValue={props.password}
        rules={[
          {
            required: true,
            message: "Please input your password!",
          },
        ]}
        hasFeedback
      >
        <Input.Password />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          저장
        </Button>
      </Form.Item>
      <Row>
        <Col offset={8}>
          <Button style={{ marginLeft: "10px" }}>회원 탈퇴</Button>
        </Col>
      </Row>
    </Form>
  );
};

export default SettingPage;
