import { Button } from "antd";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Review.css"; // Review 컴포넌트를 위한 CSS 파일 import
import React from "react";
import axios from "axios";

//props = roomPeople에 방에 있는 사람 정보 다 들어있음
const Review = (props) => {
  const [userReviews, setUserReviews] = useState([]);
  const [result, setResult] = useState();
  const [totalAbsence, setAbsence] = useState();
  const [totalLate, setLate] = useState();
  const [totalAttend, setAttend] = useState();
  const [users, setUser] = useState([]);
  const navigate = useNavigate();
  const onFinish = () => {
    const review = userReviews.map((value) => ({
      userId: value.id,
      numAttend: value.attend,
      numLate: value.late,
      numNotAttend: value.absence,
    }));
    console.log("review", review);
    axios({
      method: "post",
      url: "/appointment-score",
      headers: {
        "Content-Type": `application/json`,
      },
      data: {
        roomId: props.id,
          evaluationInfoList: review,
      },
      withCredentials: true,
    })
      .then((response) => {
        console.log("----------------", response);
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data);
      });
    // navigate("/");
  };
  // const users = props.roomPeople;
  // console.log(users);
  useEffect(() => {
    setUser(props.roomPeople);
  }, [props.roomPeople]);

  useEffect(() => {
    const initialUserReviews = users
      .map(
        (user) =>
          user.id !== props.MY_USER_ID && {
            id: user.id,
            name: user.name,
            attend: 0,
            late: 0,
            absence: 0,
          }
      )
      .filter(Boolean);
    setUserReviews(initialUserReviews);
  }, [users]);

  useEffect(() => {
    const userReview = userReviews.map((review, index) => (
      <div key={index}>
        <span>{review.name + "에 대한 평가 "}</span>
        <Button
          onClick={() => handleReviewClick(index, "attend")}
          type={review.attend > 0 ? "primary" : ""}
        >
          참석
        </Button>
        <Button
          onClick={() => handleReviewClick(index, "late")}
          type={review.late > 0 ? "primary" : ""}
        >
          지각
        </Button>
        <Button
          onClick={() => handleReviewClick(index, "absence")}
          type={review.absence > 0 ? "primary" : ""}
        >
          불참
        </Button>
      </div>
    ));
    setResult(userReview);

    const tempAttend = userReviews.reduce(
      (total, review) => total + review.attend,
      0
    );
    const tempLate = userReviews.reduce(
      (total, review) => total + review.late,
      0
    );
    const tempAbsence = userReviews.reduce(
      (total, review) => total + review.absence,
      0
    );

    setAbsence(tempAbsence);
    setAttend(tempAttend);
    setLate(tempLate);
  }, [userReviews]);

  const handleReviewClick = (index, type) => {
    setUserReviews((prevState) => {
      const updatedUserReviews = [...prevState];
      const selectedReview = updatedUserReviews[index];

      if (selectedReview[type] === 0) {
        selectedReview[type]++;

        Object.keys(selectedReview).forEach((key) => {
          if (key !== type && key !== "name" && key !== "id") {
            selectedReview[key] = 0;
          }
        });
      }

      return updatedUserReviews;
    });
  };

  return (
    <div className="review-container">
      <div className="review-heading">
        약속이 종료되었습니다. <br />
        상대방을 평가해주세요.
      </div>
      <div className="user-review">{result}</div>
      <span className="total-stats">
        총 인원 {userReviews.length}명 (참석 {totalAttend}명 / 지각 {totalLate}
        명 / 불참 {totalAbsence}명)
      </span>
      <Button className="to-chat-button" onClick={onFinish}>
        제출
      </Button>
    </div>
  );
};

export default Review;
