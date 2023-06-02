// FriendSearch.js에서
import React, { useState } from "react"; // useState를 불러옵니다.
import "./FriendSearch.css";
export default function FriendSearch(props) {
  const { handleSearch } = props; // handleSearch를 prop에서 가져옵니다.

  return (
    <div className="friend-search">
      <input
        type="search"
        className="friend-search-input"
        placeholder="Search Friend"
        onChange={handleSearch} // handleSearch를 이 부분에 할당해줍니다.
      />
    </div>
  );
}
