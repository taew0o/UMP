import {
  AutoComplete,
  Button,
  Cascader,
  Checkbox,
  Col,
  Form,
  Input,
  InputNumber,
  Row,
  Select,
} from "antd";
import axios from "axios";
import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./RegisterPage.css";
const { Option } = Select;
const formItemLayout = {
  labelCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 8,
    },
  },
  wrapperCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 16,
    },
  },
};
const tailFormItemLayout = {
  wrapperCol: {
    xs: {
      span: 24,
      offset: 0,
    },
    sm: {
      span: 16,
      offset: 8,
    },
  },
};
const RegisterPage = () => {
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const onFinish = (values) => {
    console.log("Received values of form: ", values);
    axios({
      method: "post",
      url: "/signup",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        id: values.id,
        password: values.password,
        name: values.nickname,
        phone_num: values.phone,
      },
    })
      .then((response) => {
        console.log(response.data);
        alert(`반갑습니다! ${response.data.name}님`);
        navigate("/login");
      })
      .catch(function (error) {
        console.log(error.response);
        alert(error.response.data);
      });
  };

  return (
    <div className="register-container">
      <Form
        {...formItemLayout}
        form={form}
        name="register"
        onFinish={onFinish}
        initialValues={{
          prefix: "86",
        }}
        scrollToFirstError
      >
        <Form.Item
          name="id"
          label="아이디"
          className="register-input"
          rules={[
            {
              required: true,
              message: "아이디를 입력하세요",
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          name="password"
          label="비밀번호"
          rules={[
            {
              required: true,
              message: "비밀번호를 입력하세요",
            },
          ]}
          hasFeedback
        >
          <Input.Password className="register-input" />
        </Form.Item>

        <Form.Item
          name="confirm"
          label="비밀번호 확인"
          dependencies={["password"]}
          hasFeedback
          rules={[
            {
              required: true,
              message: "비밀번호 확인을 해주세요",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue("password") === value) {
                  return Promise.resolve();
                }
                return Promise.reject(
                  new Error("입력된 두 비밀번호가 일치하지 않습니다")
                );
              },
            }),
          ]}
        >
          <Input.Password className="register-input" />
        </Form.Item>

        <Form.Item
          name="nickname"
          label="이름"
          tooltip="불리고 싶은 이름을 입력해주세요"
          rules={[
            {
              required: true,
              message: "이름을 입력해주세요",
              whitespace: true,
            },
          ]}
        >
          <Input className="register-input" />
        </Form.Item>

        <Form.Item
          name="phone"
          label="전화번호"
          rules={[
            {
              required: true,
              message: "전화번호를 입력해주세요",
            },
          ]}
        >
          <Input
            // addonBefore={prefixSelector}
            style={{
              width: "100%",
            }}
          />
        </Form.Item>

        <Form.Item
          name="intro"
          label="자기소개"
          rules={[
            {
              required: true,
              message: "자기소개를 써주세요",
            },
          ]}
        >
          <Input.TextArea showCount maxLength={100} />
        </Form.Item>

        <Form.Item
          name="gender"
          label="성별"
          rules={[
            {
              required: true,
              message: "성별을 입력해주세요",
            },
          ]}
        >
          <Select placeholder="select your gender">
            <Option value="male">남성</Option>
            <Option value="female">여성</Option>
          </Select>
        </Form.Item>

        <Form.Item
          name="agreement"
          valuePropName="checked"
          rules={[
            {
              validator: (_, value) =>
                value
                  ? Promise.resolve()
                  : Promise.reject(new Error("Should accept agreement")),
            },
          ]}
          {...tailFormItemLayout}
        >
          <Checkbox>
            가입에 <a href="">동의</a>
          </Checkbox>
        </Form.Item>
        <Form.Item {...tailFormItemLayout}>
          <Button type="primary" htmlType="submit">
            가입
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};
export default RegisterPage;
