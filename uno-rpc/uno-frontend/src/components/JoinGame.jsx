import React, { useState } from "react";
import styles from "../styles/JoinGame.module.css";
import { joinGame } from "../services/UnoService";

const JoinGame = ({ onJoined }) => {
  const [playerName, setPlayerName] = useState("");
  const [gameId, setGameId] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const handleJoin = async () => {
    if (!playerName) return alert("Enter your name");

    setLoading(true);
    setMessage("");
    try {
      const resp = await joinGame([playerName], gameId);
      console.log("JoinGame response:", resp);

      const allPlayers = resp.allPlayers?.map((p, i) => ({
        id: p.id,
        name: p.name || `Player ${i + 1}`,
        cards: [],
        color: null, 
      })) || [];

      const newPlayers = resp.newPlayers?.map((p, i) => ({
        id: p.id,
        name: p.name || `Player ${i + 1}`,
        cards: [],
        color: null,
      })) || [];

      setMessage(resp.message);

      onJoined(
        {
          gameId: resp.gameId,
          allPlayers,
          newPlayers,
          message: resp.message,
        },
        playerName
      );
    } catch (err) {
      console.error(err);
      setMessage("Failed to join game. Please try again.");
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

        {message && <p className={styles.message}>{message}</p>}
      </div>
    </div>
  );
};

export default JoinGame;
