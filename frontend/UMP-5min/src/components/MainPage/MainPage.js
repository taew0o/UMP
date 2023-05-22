import React from "react";
import ConversationList from "../ConversationList";
// import MessageList from "../MessageList";
import "./Messenger.css";
import MainToolBar from "../MainToolBar";

export default function MainPage(props) {
  return (
    <div className="messenger">
      {/* <Toolbar
          title="Messenger"
          leftItems={[
            <ToolbarButton key="cog" icon="ion-ios-cog" />
          ]}
          rightItems={[
            <ToolbarButton key="add" icon="ion-ios-add-circle-outline" />
          ]}
        /> */}

      {/* <Toolbar
          title="Conversation Title"
          rightItems={[
            <ToolbarButton key="info" icon="ion-ios-information-circle-outline" />,
            <ToolbarButton key="video" icon="ion-ios-videocam" />,
            <ToolbarButton key="phone" icon="ion-ios-call" />
          ]}
        /> */}

      <div className="scrollable sidebar">
        <MainToolBar />
      </div>

      <div className="scrollable content">
        {/* <MessageList /> */}
        <ConversationList />
      </div>
    </div>
  );
}
