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

const SettingPage = () => {
  const [form] = Form.useForm();
  const [myData, setMyData] = useState({});
  useEffect(() => {
    getData();
    console.log(myData);
  }, []);

  function getData() {
    const myId = cookie.load("userId");
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
        // form.setFieldValue({
        //   nickname: response.data.name,
        // });
      })
      .catch(function (error) {
        console.log(error);
        alert(`에러 발생 관리자 문의하세요!`);
      });
  }

  const onFinish = (values) => {
    console.log("Received values of form: ", values);
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
        // initialValue={myData.name}
        rules={[
          {
            required: true,
            message: "Please input your nickname!",
            whitespace: true,
          },
        ]}
      >
        <input defaultValue={myData.id} />
      </Form.Item>
      <Form.Item
        name="phone"
        label="전화번호"
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
        rules={[
          {
            required: true,
            message: "Please input your password!",
          },
        ]}
        hasFeedback
      >
        <Input.Password />
        <input defaultValue={myData.password} />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          저장
        </Button>
      </Form.Item>
      <Row>
        <Col offset={8}>
          <Button>로그아웃</Button>
          <Button style={{ marginLeft: "10px" }}>회원 탈퇴</Button>
        </Col>
      </Row>
    </Form>
  );
};

export default SettingPage;
