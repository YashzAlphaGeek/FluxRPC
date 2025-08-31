import React, { useState } from 'react';
import Lobby from './components/Lobby';
import GameBoard from './components/GameBoard';

function App() {
  const [gameId, setGameId] = useState(null);
  const [playerId, setPlayerId] = useState(null);

  return (
    <div className="App">
      {!gameId ? (
        <Lobby setGameId={setGameId} setPlayerId={setPlayerId} />
      ) : (
        <GameBoard gameId={gameId} playerId={playerId} />
      )}
    </div>
  );
}

export default App;
