import React from "react";
import styles from "../styles/GameHeader.module.css";

const GameHeader = ({ players, currentPlayer }) => {
  return (
    <div className={styles.sidebar}>
      <h3>Players</h3>
      <ul className={styles.playerList}>
        {players.map((p, idx) => (
          <li
            key={p.id || idx} 
            className={`${styles.playerItem} ${
              p.id === currentPlayer?.id ? styles.currentTurn : ""
            }`}
            style={{
              '--player-color': p.color || "#4a90e2",
            }}
          >
            <span className={styles.playerName}>{p.name}</span>
            <span className={styles.playerCards}>
              ({p.cards?.length || 0})
            </span>
          </li>
        ))}
      </ul>
      <div className={styles.turnInfo}>
        Current Turn: <strong>{currentPlayer?.name || "N/A"}</strong>
      </div>
    </div>
  );
};

export default GameHeader;
