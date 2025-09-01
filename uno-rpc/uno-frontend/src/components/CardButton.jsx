import React from "react";
import styles from "../styles/CardButton.module.css";

const CardButton = ({ card, onClick, disabled, playable }) => {
  const [color, value] = card.split("_"); 

  const colorMap = {
    red: "#e74c3c",
    green: "#27ae60",
    blue: "#3498db",
    yellow: "#f1c40f",
    black: "#000",
  };

  const bgColor = colorMap[color] || "#ccc";
  const textColor = color === "yellow" ? "#333" : "#fff";

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
        boxShadow: disabled
          ? "none"
          : playable
          ? `0 0 10px 3px ${bgColor}aa`
          : `0 4px 8px ${bgColor}55`,
      }}
    >
      {value.toUpperCase()}
    </button>
  );
};

export default CardButton;
