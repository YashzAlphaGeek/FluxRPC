import React, { useEffect, useState, useRef } from 'react';
import { UnoServiceClient } from '../grpc/uno_grpc_web_pb';
import { PlayRequest, GameStateRequest } from '../grpc/uno_pb';

const client = new UnoServiceClient('http://localhost:8081');

const GameBoard = ({ gameId, playerId }) => {
  const [cardsOnTable, setCardsOnTable] = useState([]);
  const [players, setPlayers] = useState([]);
  const [currentPlayer, setCurrentPlayer] = useState('');
  const playStreamRef = useRef(null);

  // Subscribe to game state updates
  useEffect(() => {
    if (!gameId) return;

    const stateReq = new GameStateRequest();
    stateReq.setGameId(gameId);

    const stateStream = client.gameState(stateReq, {});

    stateStream.on('data', (resp) => {
      const state = resp.toObject();
      setCardsOnTable(state.cardsOnTableList);
      setPlayers(state.playersList);
      setCurrentPlayer(state.currentPlayerId);
    });

    stateStream.on('error', (err) => console.error('GameState stream error:', err));
    stateStream.on('end', () => console.log('GameState stream ended'));

    return () => stateStream.cancel();
  }, [gameId]);

  // Initialize bidirectional PlayCard stream
  useEffect(() => {
    if (!gameId || !playerId) return;

    playStreamRef.current = client.playCard({}, {});

    playStreamRef.current.on('data', (resp) => {
      console.log('PlayCard response:', resp.toObject());
    });

    playStreamRef.current.on('error', (err) => console.error('PlayCard stream error:', err));
    playStreamRef.current.on('end', () => console.log('PlayCard stream ended'));

    return () => {
      if (playStreamRef.current) playStreamRef.current.cancel();
    };
  }, [gameId, playerId]);

  const playCard = (card) => {
    if (!playStreamRef.current) return;

    if (currentPlayer !== playerId) {
      alert("It's not your turn!");
      return;
    }

    const req = new PlayRequest();
    req.setGameId(gameId);
    req.setPlayerId(playerId);
    req.setCard(card);

    playStreamRef.current.write(req);
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
