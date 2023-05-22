import { Button } from "antd";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Review.css"; // Review 컴포넌트를 위한 CSS 파일 import
import React from "react";
const Review = () => {
  const navigate = useNavigate();
  const toChat = () => {
    navigate("/");
  };
  const user = ["박태우", "이새롬", "박지후"];

  const initialUserReviews = user.map((userName) => ({
    name: userName,
    attend: 0,
    late: 0,
    absence: 0,
  }));

  const [userReviews, setUserReviews] = useState(initialUserReviews);

  const handleReviewClick = (index, type) => {
    setUserReviews((prevState) => {
      const updatedUserReviews = [...prevState];
      const selectedReview = updatedUserReviews[index];

      if (selectedReview[type] === 0) {
        selectedReview[type]++;

        Object.keys(selectedReview).forEach((key) => {
          if (key !== type && key !== "name") {
            selectedReview[key] = 0;
          }
        });
      }

      return updatedUserReviews;
    });
  };

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

  const totalAttend = userReviews.reduce(
    (total, review) => total + review.attend,
    0
  );
  const totalLate = userReviews.reduce(
    (total, review) => total + review.late,
    0
  );
  const totalAbsence = userReviews.reduce(
    (total, review) => total + review.absence,
    0
  );

  return (
    <div className="review-container">
      <div className="review-heading">평가</div>
      <div className="user-review">{userReview}</div>
      <span className="total-stats">
        총 인원 {user.length}명 (참석 {totalAttend}명 / 지각 {totalLate}명 /
        불참 {totalAbsence}명)
      </span>
      <Button className="to-chat-button" onClick={toChat}>
        제출
      </Button>
    </div>
  );
};

export default Review;
