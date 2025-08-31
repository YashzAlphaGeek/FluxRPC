import React, { useState } from 'react';
import { UnoServiceClient } from '../grpc/uno_grpc_web_pb';
import { JoinRequest } from '../grpc/uno_pb';

const client = new UnoServiceClient('http://localhost:8081');

const Lobby = ({ setGameId, setPlayerId }) => {
  const [name, setName] = useState('');

  const joinGame = () => {
    if (!name) return alert('Enter your name');

    const req = new JoinRequest();
    req.setPlayernamesList([name]);

    client.joinGame(req, {}, (err, resp) => {
      if (err) {
        console.error('Failed to join game:', err);
        alert('Failed to join game');
        return;
      }

      const obj = resp.toObject();
      console.log('JoinResponse:', obj);

      setGameId(obj.gameid);
      const firstPlayerId = obj.allplayeridsList && obj.allplayeridsList[0];
      setPlayerId(firstPlayerId);
    });
  };

  return (
    <div>
      <h2>UNO Lobby</h2>
      <input
        type="text"
        placeholder="Enter your name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <button onClick={joinGame}>Join Game</button>
    </div>
  );
};

export default Lobby;
