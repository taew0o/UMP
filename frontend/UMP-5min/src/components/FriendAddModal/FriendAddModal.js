// FriendAddModal.js
import React from "react";

const FriendAddModal = ({ showModal, closeModal, addFriend }) => {
  const [newFriendId, setNewFriendId] = React.useState("");

  const handleInputChange = (event) => {
    setNewFriendId(event.target.value);
  };

  const handleSubmit = () => {
    addFriend(newFriendId);
    setNewFriendId("");
    closeModal();
  };

  return (
    <>
      {showModal ? (
        <div onClick={closeModal}>
          <div onClick={(e) => e.stopPropagation()}>
            <h3>ID를 입력하세요</h3>
            <input type="text" value={newFriendId} onChange={handleInputChange} />
            <button onClick={handleSubmit}>친구 추가</button>
          </div>
        </div>
      ) : null}
    </>
  );
};

export default FriendAddModal;
