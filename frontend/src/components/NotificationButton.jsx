import React from 'react';
import './NotificationButton.css';

export default function NotificationButton({ count, onClick }) {
  return (
    <div className="notification-button" onClick={onClick}>
      <div className="icon">
        <i className="fas fa-envelope"></i>
      </div>
      {count > 0 && <div className="badge">{count}</div>}
    </div>
  );
}
