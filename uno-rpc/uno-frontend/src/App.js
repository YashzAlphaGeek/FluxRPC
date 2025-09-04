import React, { useState } from "react";
import JoinGame from "./components/JoinGame";
import Lobby from "./components/Lobby";
import GameBoard from "./pages/GameBoard";

function App() {
  const [gameInfo, setGameInfo] = useState(null);
  const [gameStarted, setGameStarted] = useState(false);

  return (
    <>
      {!gameInfo ? (
        <JoinGame
          onJoined={(resp, playerNameInput) => {
            // Determine current player's ID
            const myPlayerId = resp.newPlayers?.[0]?.id || resp.allPlayers?.[0]?.id;

            console.log("Joined game response:", resp);

            setGameInfo({
              gameId: resp.gameId,
              playerId: myPlayerId,
              playerName: playerNameInput,
            });
          }}
        />
      ) : !gameStarted ? (
        <Lobby
          gameId={gameInfo.gameId}
          playerId={gameInfo.playerId}
          playerName={gameInfo.playerName}
          onStartGame={() => setGameStarted(true)} // matches Lobby prop
        />
      ) : (
        <GameBoard
          gameId={gameInfo.gameId}
          playerId={gameInfo.playerId}
          playerName={gameInfo.playerName}
        />
      )}
    </>
  );
}

export default App;
