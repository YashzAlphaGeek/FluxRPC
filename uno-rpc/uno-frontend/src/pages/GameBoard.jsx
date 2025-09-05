import React, { useEffect, useState } from "react";
import { subscribeGameState, playCard } from "../services/UnoService";
import CardButton from "../components/CardButton";
import GameHeader from "../components/GameHeader";
import styles from "../styles/GameBoard.module.css";
import "../styles/common.css";

const GameBoard = ({ gameId, playerId, playerName }) => {
  const [players, setPlayers] = useState([]);
  const [currentPlayer, setCurrentPlayer] = useState(null);
  const [cardsOnTable, setCardsOnTable] = useState([]);
  const [myCards, setMyCards] = useState([]);
  const [lastMoveInfo, setLastMoveInfo] = useState("");

  useEffect(() => {
    if (!gameId) return;

    const stream = subscribeGameState(
      gameId,
      (data) => {
        console.log("Game state response:", data);

        const normalizedPlayers = (data.players || []).map((p, i) => ({
          id: p.id,
          name: p.name,
          cards: (p.cards || []).map((c, j) => ({
            uid: c.uid || `p${i}_c${j}_${Date.now()}`,
            color: c.color,
            value: c.value,
          })),
        }));

        setPlayers(normalizedPlayers);
        setCardsOnTable((data.cardsOnTable || []).map((c, i) => ({
          uid: c.uid || `table_${i}_${Date.now()}`,
          color: c.color,
          value: c.value,
        })));

        const current = normalizedPlayers.find((p) => p.id === data.currentPlayerId) || null;
        setCurrentPlayer(current);

        const me = normalizedPlayers.find((p) => p.id === playerId);
        setMyCards(me?.cards || []);

        setLastMoveInfo(data.lastMoveInfo || "");
      },
      (err) => console.error("GameBoard stream error:", err),
      () => console.log("GameBoard stream ended")
    );

    return () => stream?.cancel?.();
  }, [gameId, playerId]);

  const isMyTurn = currentPlayer?.id === playerId;
  const lastCard = cardsOnTable[cardsOnTable.length - 1];

  const isCardPlayable = (card) =>
    !lastCard || card.color === lastCard.color || card.value === lastCard.value || card.color === "black";

  const handlePlayCard = (card) => {
    if (!isMyTurn) return alert("Not your turn!");
    if (!isCardPlayable(card)) return alert("Card not playable!");
    playCard(gameId, playerId, card).catch(console.error);
  };

  const radius = Math.min(200, 250 - players.length * 10);

  return (
    <div className={styles.gameWrapper}>
      <div className={styles.sidebar}>
        <GameHeader
          players={players}
          currentPlayer={currentPlayer || { id: playerId, name: playerName }}
          gameId={gameId}
        />
        {lastMoveInfo && <p className={styles.lastMove}>Last move: {lastMoveInfo}</p>}
      </div>

      <div className={styles.mainArea}>
        <div className={styles.roundTable}>
          {players.map((p, index) => {
            const angle = (360 / players.length) * index;
            const isCurrent = p.id === currentPlayer?.id;
            const isMe = p.id === playerId;

            return (
              <div
                key={p.id}
                className={`${styles.playerCircle} ${isCurrent ? styles.currentTurn : ""} ${isMe ? styles.selfPlayer : ""}`}
                style={{ transform: `rotate(${angle}deg) translate(${radius}px) rotate(-${angle}deg)` }}
                data-color={p.cards?.[0]?.color || "gray"}
              >
                <div className={styles.playerName}>{p.name}</div>
                <div className={styles.cardCount}>({p.cards?.length || 0})</div>
              </div>
            );
          })}

          <div className={styles.centerTable}>
            {cardsOnTable.length > 0 ? (
              cardsOnTable.map((c) => (
                <CardButton
                  key={c.uid}
                  card={c}
                  disabled
                  playable={false}
                  className={styles.animateCard}
                />
              ))
            ) : (
              <p className={styles.noCardsText}>(no cards yet)</p>
            )}
          </div>
        </div>

        <div className={styles.handSection}>
          <h3>Your Hand:</h3>
          <div className={styles.handCardsRow}>
            {myCards.length > 0 ? (
              myCards.map((c) => (
                <CardButton
                  key={c.uid}
                  card={c}
                  onClick={() => handlePlayCard(c)}
                  disabled={!isMyTurn}
                  playable={isMyTurn && isCardPlayable(c)}
                  className={isMyTurn && isCardPlayable(c) ? styles.animateCard : ""}
                />
              ))
            ) : (
              <p className={styles.noCardsText}>(waiting for cards...)</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default GameBoard;
