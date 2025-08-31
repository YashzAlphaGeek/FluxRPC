import React from "react";
import styles from "../styles/GameHeader.module.css";

const GameHeader = ({ players, currentPlayer }) => {
  return (
    <div className={styles.header}>
      <div>
        <strong>Players:</strong> {players.map(p => p.name || p.id).join(", ")}
      </div>
      <div>
        <strong>Current Turn:</strong> {currentPlayer.name || currentPlayer.id || ""}
      </div>
    </div>
  );
};

export default GameHeader;
