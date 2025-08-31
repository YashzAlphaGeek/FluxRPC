import React from "react";
import styles from "../styles/CardButton.module.css";

const CardButton = ({ card, onClick, disabled }) => {
  const [color, value] = card.split("_");
  const isWild = color === "WILD";

  return (
    <button
      className={`${styles.cardButton} ${disabled ? styles.disabled : ""}`}
      onClick={() => onClick(card)}
      disabled={disabled}
      style={{ backgroundColor: isWild ? "black" : color.toLowerCase(), color: isWild ? "white" : "black" }}
    >
      {value}
    </button>
  );
};

export default CardButton;
