import React, { useState } from "react";
import JoinGame from "./components/JoinGame";
import GameBoard from "./pages/GameBoard";

function App() {
  const [gameInfo, setGameInfo] = useState(null);

  return (
    <>
      {gameInfo ? (
        <GameBoard
          gameId={gameInfo.gameId}
          playerId={gameInfo.playerId}
          playerName={gameInfo.playerName}
        />
      ) : (
        <JoinGame
          onJoined={(resp, playerNameInput) => {
            const myPlayerId =
              resp.newPlayers?.[0]?.id || resp.allPlayers?.[0]?.id;

            setGameInfo({
              gameId: resp.gameId,
              playerId: myPlayerId,
              playerName: playerNameInput,
            });
          }}
        />
      )}
    </>
  );
}

export default App;
