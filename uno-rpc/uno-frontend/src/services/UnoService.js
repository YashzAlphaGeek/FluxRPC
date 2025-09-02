import { UnoServiceClient } from "../grpc/uno_grpc_web_pb";
import { JoinRequest, PlayRequest, GameStateRequest } from "../grpc/uno_pb";

const client = new UnoServiceClient("/");

// Join a game
export const joinGame = (playerNames, gameId) => {
  const req = new JoinRequest();
  req.setPlayernamesList(playerNames);
  req.setGameid(gameId);

  return new Promise((resolve, reject) => {
    client.joinGame(req, {}, (err, resp) => {
      if (err) return reject(err);

      const allPlayers = resp.getAllplayeridsList().map(p => ({
        id: p.getId(),
        name: p.getName(),
      }));

      const newPlayers = resp.getNewplayeridsList().map(p => ({
        id: p.getId(),
        name: p.getName(),
      }));

      resolve({
        message: resp.getMessage(),
        gameId: resp.getGameid(),
        allPlayers,
        newPlayers,
      });
    });
  });
};

// Play a card
export const playCard = (gameId, playerId, card) => {
  const req = new PlayRequest();
  req.setGameid(gameId);
  req.setPlayerid(playerId);
  req.setCard(card);

  return new Promise((resolve, reject) => {
    client.play(req, {}, (err, resp) => {
      if (err) return reject(err);
      resolve(resp.toObject());
    });
  });
};

export const subscribeGameState = (gameId, onData, onError, onEnd) => {
  const req = new GameStateRequest();
  req.setGameid(gameId);

  const stream = client.gameState(req, {});
  stream.on("data", resp => {
    const state = resp.toObject ? resp.toObject() : resp;

    const protoPlayers = resp.getPlayersList ? resp.getPlayersList() : state.players || [];
    const players = protoPlayers.map((p, i) => ({
      id: p.getId ? p.getId() : `player-${i}`,
      name: p.getName ? p.getName() : `Player ${i + 1}`,
      cards: [], 
    }));

    const protoCards = resp.getCardsOnTableList ? resp.getCardsOnTableList() : state.cardsOnTable || [];
    const cardsOnTable = protoCards.map((c, i) => {
      const [color, value] = (c || "").split("_");
      return { uid: `card_${i}`, color: color || "black", value: value || "" };
    });

    const currentPlayerId = resp.getCurrentPlayerId ? resp.getCurrentPlayerId() : state.currentPlayerId || null;

    onData({ players, cardsOnTable, currentPlayerId });
  });

  stream.on("error", err => onError?.(err));
  stream.on("end", () => onEnd?.());

  return stream;
};
