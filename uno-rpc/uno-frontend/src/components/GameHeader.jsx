import React, { useState } from "react";
import styles from "../styles/GameHeader.module.css";

const GameHeader = ({ players = [], currentPlayer, gameId }) => {
  const [copied, setCopied] = useState(false);

  const handleCopy = () => {
    if (!gameId) return;
    navigator.clipboard.writeText(gameId).then(() => {
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    });
  };

  return (
    <div className={styles.sidebar}>
      <h3>UNO Game</h3>

      {gameId && (
        <div className={styles.gameIdContainer}>
          <span className={styles.gameId}>Game ID: {gameId}</span>
          <button className={styles.copyButton} onClick={handleCopy}>
            {copied ? "✔" : "📋"}
          </button>
        </div>
      )}

      <ul className={styles.playerList}>
        {players.map((p, idx) => {
          const isCurrent = p.id === currentPlayer?.id;
          return (
            <li
              key={p.id || `player-${idx}`}
              className={`${styles.playerItem} ${isCurrent ? styles.currentTurn : ""}`}
              style={{ '--player-color': p.color || "#4a90e2" }}
            >
              <span className={styles.playerName}>
                {p.name || `Player ${idx + 1}`}
                {isCurrent && " 🔹"}
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
