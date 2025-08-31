import { UnoServiceClient } from "../grpc/uno_grpc_web_pb";
import { JoinRequest, PlayRequest, GameStateRequest } from "../grpc/uno_pb";

const client = new UnoServiceClient('/');

export const joinGame = (playerNames, gameId) => {
  const req = new JoinRequest();

  req.setPlayernamesList(playerNames); 

  req.setGameid(gameId);

  return new Promise((resolve, reject) => {
    client.joinGame(req, {}, (err, resp) => {   
      if (err) reject(err);
      else resolve(resp.toObject());
    });
  });
};

export const playCard = (gameId, playerId, card, playStream) => {
  const req = new PlayRequest();
  req.setGameid(gameId);
  req.setPlayerid(playerId);
  req.setCard(card);

  if (playStream) {
    playStream.write(req);
  } else {
    return new Promise((resolve, reject) => {
      client.play(req, {}, (err, resp) => {
        if (err) reject(err);
        else resolve(resp.toObject());
      });
    });
  }
};

export const subscribeGameState = (gameId, onData, onError, onEnd) => {
  const req = new GameStateRequest();
  req.setGameid(gameId);
  const stream = client.gameState(req, {});
  stream.on("data", onData);
  stream.on("error", onError);
  stream.on("end", onEnd);
  return stream;
};
