import React, { useState } from "react";
import { joinGame } from "../services/UnoService";
import { Button, TextField, Card, CardContent, Typography } from "@mui/material";

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

      // Use newplayeridsList if available, else fallback to first allplayeridsList
      const myPlayerId = resp.newplayeridsList?.[0] || resp.allplayeridsList?.[0];

      // Pass to parent
      onJoined({ gameId: resp.gameid, playerId: myPlayerId });
    } catch (err) {
      console.error(err);
      alert("Failed to join game");
    }
    setLoading(false);
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        minHeight: "100vh",
        background: "linear-gradient(135deg, #7f00ff, #e100ff)"
      }}
    >
      <Card sx={{ width: 400, p: 2 }}>
        <CardContent>
          <Typography variant="h5" textAlign="center" mb={2}>
            🎲 Join UNO Game
          </Typography>
          <TextField
            fullWidth
            label="Your Name"
            value={playerName}
            onChange={(e) => setPlayerName(e.target.value)}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Game ID (optional)"
            value={gameId}
            onChange={(e) => setGameId(e.target.value)}
            margin="normal"
          />
          <Button
            fullWidth
            variant="contained"
            color="primary"
            onClick={handleJoin}
            disabled={loading}
            sx={{ mt: 2 }}
          >
            {loading ? "Joining..." : "Join Game"}
          </Button>
        </CardContent>
      </Card>
    </div>
  );
};

export default JoinGame;
