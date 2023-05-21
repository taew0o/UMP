import React, { useState, useEffect } from "react";
import ConversationSearch from "../ConversationSearch";
import ConversationListItem from "../ConversationListItem";
import Toolbar from "../Toolbar";
import ToolbarButton from "../ToolbarButton";
import axios from "axios";

import "./FriendPage.css";
import FriendSearch from "../FriendSearch";
import FriendList from "../FriendList/FriendList";

const FriendPage = (props) => {
  const [conversations, setConversations] = useState([]);
  useEffect(() => {
    getConversations();
  }, []);

  const getConversations = () => {
    axios.get("https://randomuser.me/api/?results=20").then((response) => {
      let newConversations = response.data.results.map((result) => {
        return {
          photo: result.picture.large,
          name: `${result.name.first} ${result.name.last}`,
          text: "친구 정보",
        };
      });
      setConversations([...conversations, ...newConversations]);
    });
  };

  return (
    <div className="friend-list">
      <Toolbar
        title="Friend"
        leftItems={[<ToolbarButton key="cog" icon="ion-ios-cog" />]}
        rightItems={[
          <ToolbarButton key="add" icon="ion-ios-add-circle-outline" />,
        ]}
      />
      <FriendSearch />
      {conversations.map((conversation) => (
        <FriendList key={conversation.name} data={conversation} />
      ))}
    </div>
  );
};
export default FriendPage;
