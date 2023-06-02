import React from 'react';
import moment from 'moment';
import './Message.css';

export default function Message(props) {
  const {
    data,
    isMine,
    startsSequence,
    endsSequence,
    showTimestamp,
    senderName // Add the sender's name as a prop
  } = props;

  const friendlyTimestamp = moment(data.timestamp).format('LLLL');
  
  return (
    <div className={[
      'message',
      `${isMine ? 'mine' : ''}`,
      `${startsSequence ? 'start' : ''}`,
      `${endsSequence ? 'end' : ''}`
    ].join(' ')}>
      {showTimestamp && (
        <div className="timestamp">
          {friendlyTimestamp}
        </div>
      )}

      <div className="bubble-container">
        <div className="bubble" title={friendlyTimestamp}>
          {!isMine && ( // Render the sender's name if the message is not from the current user
            <div className="sender-name">
              {senderName}
            </div>
          )}
          {data.message}
        </div>
      </div>
    </div>
  );
}
