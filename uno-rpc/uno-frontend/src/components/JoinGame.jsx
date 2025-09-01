import React, { useState } from "react";
import styles from "../styles/JoinGame.module.css";
import { joinGame } from "../services/UnoService";

const JoinGame = ({ onJoined }) => {
  const [playerName, setPlayerName] = useState("");
  const [gameId, setGameId] = useState("");
  const [loading, setLoading] = useState(false);

  const handleJoin = async () => {
    if (!playerName) return alert("Enter your name");
    setLoading(true);
    try {
      const resp = await joinGame([playerName], gameId);
      console.log("JoinGame response:", resp);
      onJoined(resp, playerName); 
    } catch (err) {
      console.error(err);
      alert("Failed to join game");
    }
    setLoading(false);
  };

  return (
    <div className={styles.container}>
      <div className={styles.card}>
        <h2>Join UNO Game</h2>
        <input
          type="text"
          placeholder="Your Name"
          value={playerName}
          onChange={(e) => setPlayerName(e.target.value)}
        />
        <input
          type="text"
          placeholder="Game ID (optional)"
          value={gameId}
          onChange={(e) => setGameId(e.target.value)}
        />
        <button onClick={handleJoin} disabled={loading}>
          {loading ? "Joining..." : "Join Game"}
        </button>
      </div>
    </div>
  );
};

export default JoinGame;
