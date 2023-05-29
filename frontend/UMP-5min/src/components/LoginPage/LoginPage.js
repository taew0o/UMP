import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Login.css';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input, Card } from 'antd';
import axios from 'axios';
import cookie from 'react-cookies';

const LoginPage = () => {
    const navigate = useNavigate();
    const toRegister = () => {
        navigate('/register');
    };
    const onFinish = (values) => {
        console.log('Received values of form: ', values);
        console.log(values.id, values.password);
        axios({
            method: 'post',
            url: 'http://localhost:8080/login',
            headers: {
                'Content-Type': `application/json`,
            },
            data: {
                id: values.id,
                password: values.password,
            },
            proxy: 'http://localhost:8080/login',
        })
            .then((response) => {
                console.log('--------------------', response.data);
            })
            .catch(function (error) {
                console.log(error);
            });
        const expires = new Date();
        expires.setMinutes(expires.getMinutes() + 60);
        cookie.save('userId', values.id, {
            path: '/',
            expires,
            // secure : true,
            // httpOnly : true
        });
    };
    return (
        <div className="login-container">
            <Card title="UMP" style={{ width: 400, padding: '34px' }}>
                <Form
                    name="normal_login"
                    className="login-form"
                    initialValues={{
                        remember: true,
                    }}
                    onFinish={onFinish}
                    style={{ fontSize: '34px' }}
                >
                    <Form.Item
                        name="id"
                        rules={[
                            {
                                required: true,
                                message: 'ID를 입력하세요',
                            },
                        ]}
                    >
                        <Input
                            prefix={<UserOutlined className="site-form-item-icon" />}
                            placeholder="아이디"
                            style={{ height: '50px', fontSize: '24px' }}
                        />
                    </Form.Item>
                    <Form.Item
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: '비밀번호를 입력하세요',
                            },
                        ]}
                    >
                        <Input
                            prefix={<LockOutlined className="site-form-item-icon" />}
                            type="password"
                            placeholder="비밀번호"
                            style={{ height: '50px', fontSize: '24px' }}
                        />
                    </Form.Item>
                    <Form.Item>
                        <Button
                            type="primary"
                            htmlType="submit"
                            className="login-form-button"
                            style={{ height: '60px', fontSize: '24px' }}
                        >
                            로그인
                        </Button>
                        <Button
                            type="primary"
                            htmlType="submit"
                            className="register-form-button"
                            onClick={toRegister}
                            style={{
                                height: '60px',
                                fontSize: '24px',
                                marginTop: '16px',
                            }}
                        >
                            회원가입
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default LoginPage;
