import React, { useEffect, useState } from 'react';
import { UnoServiceClient } from '../grpc/uno_grpc_web_pb';
import { PlayRequest, GameStateRequest } from '../grpc/uno_pb';

const client = new UnoServiceClient('/');

const GameBoard = ({ gameId, playerId }) => {
  const [cardsOnTable, setCardsOnTable] = useState([]);
  const [players, setPlayers] = useState([]);
  const [currentPlayer, setCurrentPlayer] = useState('');

  // Game state stream
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

    stateStream.on('error', console.error);
    stateStream.on('end', () => console.log('GameState stream ended'));

    return () => stateStream.cancel();
  }, [gameId]);

  // Play card stream
  const [playStream, setPlayStream] = useState(null);

  useEffect(() => {
    if (!gameId) return;

    const stream = client.playCard();

    stream.on('data', (resp) => {
      console.log('PlayResponse:', resp.toObject());
      // optionally update local state if needed
    });

    stream.on('error', console.error);
    stream.on('end', () => console.log('PlayCard stream ended'));

    setPlayStream(stream);

    return () => stream.cancel();
  }, [gameId]);

  const playCard = (card) => {
    if (currentPlayer !== playerId) {
      alert("It's not your turn!");
      return;
    }
    if (!playStream) return;

    const req = new PlayRequest();
    req.setGameid(gameId);
    req.setPlayerid(playerId);
    req.setCard(card);

    playStream.write(req); // send request through the stream
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
