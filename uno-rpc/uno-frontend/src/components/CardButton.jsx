import React from "react";
import styles from "../styles/CardButton.module.css";

const CardButton = ({ card, onClick, disabled, playable }) => {
  if (!card) return null;

  const { color, value } = card;

  const colorMap = {
    red: "#e74c3c",
    green: "#27ae60",
    blue: "#3498db",
    yellow: "#f1c40f",
    black: "#000000",
    purple: "#9b59b6",
    orange: "#e67e22",
  };

  const bgColor = colorMap[color.toLowerCase()] || "#ccc";
  const textColor = color.toLowerCase() === "yellow" ? "#333" : "#fff";

  return (
    <button
      className={`${styles.cardButton} ${playable ? styles.playable : ""}`}
      onClick={onClick}
      disabled={disabled}
      style={{
        backgroundColor: bgColor,
        color: textColor,
        border: `2px solid ${textColor}33`,
        cursor: disabled ? "not-allowed" : "pointer",
        transition: "transform 0.2s, box-shadow 0.2s",
        boxShadow: disabled
          ? "none"
          : playable
          ? `0 0 12px 4px ${bgColor}aa` 
          : `0 4px 8px ${bgColor}55`,
      }}
      onMouseEnter={(e) => {
        if (playable && !disabled) e.currentTarget.style.transform = "scale(1.1)";
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.transform = "scale(1)";
      }}
    >
      {String(value).toUpperCase()}
    </button>
  );
};

export default CardButton;
