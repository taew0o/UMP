import React from "react";
import { Button, Popover, Input } from "antd";
import "./Toolbar.css";

export default function Toolbar(props) {
  const { title, leftItems, rightItems, popoverContent } = props;

  const [visible, setVisible] = React.useState(false);

  const handleAddButtonClick = () => {
    // This line is no longer necessary
    // setVisible(!visible);
  };

  const handleVisibleChange = (visible) => {
    setVisible(visible);
  };

  const rightItemsWithPopover = rightItems.map((item, index) => {
    if (item.key === "add") {
      return (
        <Popover
          key={index}
          content={popoverContent}
          trigger="click"
          onVisibleChange={handleVisibleChange}
        >
          <div onClick={handleAddButtonClick}>{item}</div>
        </Popover>
      );
    } else {
      return item;
    }
  });

  return (
    <div className="toolbar">
      <div className="left-items">{leftItems}</div>
      <h1 className="toolbar-title">{title}</h1>
      <div className="right-items">{rightItemsWithPopover}</div>
    </div>
  );
}
