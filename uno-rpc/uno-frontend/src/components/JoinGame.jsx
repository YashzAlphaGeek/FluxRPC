import React, { useState } from "react";
import styles from "../styles/JoinGame.module.css";
import { joinGame } from "../services/UnoService";

const JoinGame = ({ onJoined }) => {
  const [playerName, setPlayerName] = useState("");
  const [gameId, setGameId] = useState("");
  const [loading, setLoading] = useState(false);
  const [players, setPlayers] = useState([]);
  const [newPlayers, setNewPlayers] = useState([]);
  const [message, setMessage] = useState("");

  const handleJoin = async () => {
    if (!playerName) return alert("Enter your name");
    setLoading(true);
    try {
      const resp = await joinGame([playerName], gameId);
      console.log("JoinGame response:", resp);

      // Set state to display players and message
      setPlayers(resp.allPlayers);
      setNewPlayers(resp.newPlayers);
      setMessage(resp.message);

      // Optional callback
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

        {message && <p className={styles.message}>{message}</p>}

        {players.length > 0 && (
          <div className={styles.players}>
            <h3>All Players:</h3>
            <ul>
              {players.map((p) => (
                <li key={p.id}>
                  {p.name} (ID: {p.id})
                </li>
              ))}
            </ul>
          </div>
        )}

        {newPlayers.length > 0 && (
          <div className={styles.newPlayers}>
            <h3>New Players Joined:</h3>
            <ul>
              {newPlayers.map((p) => (
                <li key={p.id}>
                  {p.name} (ID: {p.id})
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default JoinGame;
