import React from "react";
import styles from "../styles/GameHeader.module.css";

const GameHeader = ({ players = [], currentPlayer }) => {
  return (
    <div className={styles.sidebar}>
      <h3>Players</h3>
      <ul className={styles.playerList}>
        {players.map((p, idx) => {
          const isCurrent = p.id === currentPlayer?.id;
          return (
            <li
              key={p.id || `player-${idx}`}
              className={`${styles.playerItem} ${isCurrent ? styles.currentTurn : ""}`}
              style={{
                '--player-color': p.color || "#4a90e2",
              }}
            >
              <span className={styles.playerName}>
                {p.name || `Player ${idx + 1}`}
                {isCurrent && " 🔹"} {/* optional highlight for current player */}
              </span>
              <span className={styles.playerCards}>
                ({p.cards?.length || 0})
              </span>
            </li>
          );
        })}
      </ul>
      <div className={styles.turnInfo}>
        Current Turn: <strong>{currentPlayer?.name || "N/A"}</strong>
      </div>
    </div>
  );
};

export default GameHeader;
