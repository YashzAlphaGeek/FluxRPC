import { UnoServiceClient } from "../grpc/uno_grpc_web_pb";
import {
  JoinRequest,
  PlayRequest,
  GameStateRequest,
  Card as ProtoCard,
} from "../grpc/uno_pb";

const client = new UnoServiceClient("/");

const normalizeCards = (cards = [], prefix = "card") =>
  cards.map((c, i) => ({
    uid: `${prefix}_${i}_${Date.now()}`,
    color: c.getColor(),
    value: c.getValue(),
  }));

// Join a game
export const joinGame = (playerNames, gameId) => {
  const req = new JoinRequest();
  req.setPlayernamesList(playerNames);
  req.setGameid(gameId);

  return new Promise((resolve, reject) => {
    client.joinGame(req, {}, (err, resp) => {
      if (err) return reject(err);

      const allPlayers = resp.getAllplayeridsList().map((p, i) => ({
        id: p.getId(),
        name: p.getName(),
        cards: normalizeCards(p.getHandList(), `all_${i}`),
      }));

      const newPlayers = resp.getNewplayeridsList().map((p, i) => ({
        id: p.getId(),
        name: p.getName(),
        cards: normalizeCards(p.getHandList(), `new_${i}`),
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

  const protoCard = new ProtoCard();
  protoCard.setColor(card.color);
  protoCard.setValue(card.value);
  req.setCard(protoCard);

  return new Promise((resolve, reject) => {
    client.playCard(req, {}, (err, resp) => {
      if (err) return reject(err);
      resolve(resp.toObject());
    });
  });
};

// Subscribe to game state
export const subscribeGameState = (gameId, onData, onError, onEnd) => {
  const req = new GameStateRequest();
  req.setGameid(gameId);

  const stream = client.gameState(req, {});
  stream.on("data", (resp) => {
    const protoPlayers = resp.getPlayersList?.() || [];
    const players = protoPlayers.map((p, i) => ({
      id: p.getId(),
      name: p.getName(),
      cards: normalizeCards(p.getHandList(), `p${i}`),
    }));

    const protoCards = resp.getCardsOnTableList?.() || [];
    const cardsOnTable = normalizeCards(protoCards, "table");

    const currentPlayerId = resp.getCurrentPlayerId?.() || null;

    onData({ players, cardsOnTable, currentPlayerId });
  });

  stream.on("error", (err) => onError?.(err));
  stream.on("end", () => onEnd?.());

  return stream;
};
