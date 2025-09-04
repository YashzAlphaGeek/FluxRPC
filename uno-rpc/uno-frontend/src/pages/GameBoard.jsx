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

  useEffect(() => {
    if (!gameId) return;

    const stream = subscribeGameState(
      gameId,
      (resp) => {
        console.log("GameState response:", resp);

        setPlayers(resp.players || []);
        setCardsOnTable(resp.cardsOnTable || []);

        const current = (resp.players || []).find(p => p.id === resp.currentPlayerId);
        setCurrentPlayer(current || null);

        const me = (resp.players || []).find(p => p.id === playerId);
        setMyCards(me?.cards || []);
      },
      console.error,
      () => console.log("GameState stream ended")
    );

    return () => stream.cancel();
  }, [gameId, playerId]);

  const isMyTurn = currentPlayer?.id === playerId;
  const lastCard = cardsOnTable[cardsOnTable.length - 1];

  const isCardPlayable = (card) => {
    if (!lastCard) return true;
    return (
      card.color === lastCard.color ||
      card.value === lastCard.value ||
      card.color === "black"
    );
  };

  const handlePlayCard = (card) => {
    if (!isMyTurn) return alert("Not your turn!");
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
      </div>

      <div className={styles.mainArea}>
        <div className={styles.roundTable}>
          {players.map((p, index) => {
            const angle = (360 / players.length) * index;
            return (
              <div
                key={p.id}
                className={`${styles.playerCircle} ${
                  p.id === currentPlayer?.id ? styles.currentTurn : ""
                }`}
                data-color={p.color}
                style={{
                  transform: `rotate(${angle}deg) translate(${radius}px) rotate(-${angle}deg)`,
                }}
              >
                <div>{p.name}</div>
                <div>({p.cards?.length || 0})</div>
              </div>
            );
          })}

          <div className={styles.centerTable}>
            {cardsOnTable.length > 0 ? (
              cardsOnTable.map((c) => (
                <CardButton
                  key={c.uid}
                  card={c}
                  disabled={!isMyTurn}
                  playable={isMyTurn}
                />
              ))
            ) : (
              <p className={styles.noCardsText}>(none yet)</p>
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
                />
              ))
            ) : (
              <p className={styles.noCardsText}>(no cards yet)</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default GameBoard;
