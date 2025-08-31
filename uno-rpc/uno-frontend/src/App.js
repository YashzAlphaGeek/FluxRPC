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
          onJoined={(resp) => {
            const myPlayerId = resp.newplayeridsList?.[0] || resp.allplayeridsList?.[0];
            setGameInfo({
              gameId: resp.gameid,
              playerId: myPlayerId,
              playerName: myPlayerId 
            });
          }} 
        />
      )}
    </>
  );
}

export default App;
