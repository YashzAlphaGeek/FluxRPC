import React, { useEffect, useState } from 'react';
import { UnoServiceClient } from '../grpc/uno_grpc_web_pb';
import { PlayRequest, GameStateRequest } from '../grpc/uno_pb';

const client = new UnoServiceClient('/');

const GameBoard = ({ gameId, playerId }) => {
  const [cardsOnTable, setCardsOnTable] = useState([]);
  const [players, setPlayers] = useState([]);
  const [currentPlayer, setCurrentPlayer] = useState('');

  // Subscribe to game state updates (server streaming)
  useEffect(() => {
    if (!gameId) return;

    const stateReq = new GameStateRequest();
    stateReq.setGameid(gameId);

    const stateStream = client.gameState(stateReq, {});

    stateStream.on('data', (resp) => {
      const state = resp.toObject();
      setCardsOnTable(state.cardsontableList || []);
      setPlayers(state.playersList || []);
      setCurrentPlayer(state.currentplayerid || '');
    });

    stateStream.on('error', (err) => console.error(err));
    stateStream.on('end', () => console.log('GameState stream ended'));

    return () => stateStream.cancel();
  }, [gameId]);

  // Play a card (unary RPC)
  const playCard = (card) => {
    if (currentPlayer !== playerId) {
      alert("It's not your turn!");
      return;
    }

    const req = new PlayRequest();
    req.setGameid(gameId);
    req.setPlayerid(playerId);
    req.setCard(card);

    client.play(req, {}, (err, resp) => {
      if (err) {
        console.error('Play error:', err.message);
        return;
      }
      console.log('Play response:', resp.toObject());
    });
  };

  const isMyTurn = currentPlayer === playerId;

  return (
    <div>
      <h2>UNO Game</h2>
      <p>Players: {players.join(', ')}</p>
      <p>Current Turn: {currentPlayer}</p>

      <h3>Cards on Table:</h3>
      <ul>
        {cardsOnTable.map((c, i) => (
          <li key={i}>{c}</li>
        ))}
      </ul>

      <h3>Play a Card</h3>
      <button onClick={() => playCard('RED_7')} disabled={!isMyTurn}>Play RED_7</button>
      <button onClick={() => playCard('GREEN_7')} disabled={!isMyTurn}>Play GREEN_7</button>
      <button onClick={() => playCard('BLUE_7')} disabled={!isMyTurn}>Play BLUE_7</button>
    </div>
  );
};

export default GameBoard;
