import React, { useEffect, useState } from "react";
import { subscribeGameState, playCard } from "../services/UnoService";
import CardButton from "../components/CardButton";
import GameHeader from "../components/GameHeader";
import styles from "../styles/GameBoard.module.css";
import '../styles/common.css';

const COLORS = ["red", "green", "yellow", "blue", "purple", "orange"];

const GameBoard = ({ gameId, playerId, playerName }) => {
  const [players, setPlayers] = useState([{ id: playerId, name: playerName, cards: [], color: COLORS[0] }]);
  const [currentPlayer, setCurrentPlayer] = useState(null);
  const [cardsOnTable, setCardsOnTable] = useState([]);
  const [myCards, setMyCards] = useState([]);

  useEffect(() => {
    if (!gameId) return;

    const stream = subscribeGameState(
      gameId,
      (resp) => {
        const state = resp.toObject();

        if (state.playersList?.length) {
          setPlayers(
            state.playersList.map((p, i) => ({
              id: p.id,
              name: p.name && p.name.trim() !== "" ? p.name : `Player ${i + 1}`,
              color: COLORS[i % COLORS.length],
              cards: (p.cards || []).map((c, j) => ({
                ...c,
                uid: `${c.color}_${c.value}_${j}_${i}`, // unique per card
              })),
            }))
          );
        }

        const current = state.playersList?.find((p) => p.id === state.currentplayerid);
        setCurrentPlayer(current || null);

        setCardsOnTable(
          (state.cardsontableList || []).map((c, i) => ({
            ...c,
            uid: `${c.color}_${c.value}_${i}`, // unique key for table cards
          }))
        );

        const me = state.playersList?.find((p) => p.id === playerId);
        setMyCards(
          (me?.cards || []).map((c, i) => ({
            ...c,
            uid: `${c.color}_${c.value}_${i}`, // unique key for hand cards
          }))
        );
      },
      console.error,
      () => console.log("GameState stream ended")
    );

    return () => stream.cancel();
  }, [gameId, playerId]);

  const isMyTurn = currentPlayer?.id === playerId;
  const radius = Math.min(200, 250 - players.length * 10);

  const lastCard = cardsOnTable[cardsOnTable.length - 1];
  const isCardPlayable = (card) => {
    if (!lastCard) return true;
    return card.color === lastCard.color || card.value === lastCard.value || card.color === "black";
  };

  const handlePlayCard = (card) => {
    if (!isMyTurn) return alert("Not your turn!");
    playCard(gameId, playerId, card).catch(console.error);
  };

  return (
    <div className={styles.gameWrapper}>
      {/* Sidebar */}
      <div className={styles.sidebar}>
        <GameHeader
          players={players}
          currentPlayer={currentPlayer || { id: playerId, name: playerName }}
        />
      </div>

      {/* Main Area */}
      <div className={styles.mainArea}>
        <div className={styles.roundTable}>
          {players.map((p, index) => {
            const angle = (360 / players.length) * index;
            return (
              <div
                key={p.id} // unique key for each player
                className={`${styles.playerCircle} ${p.id === currentPlayer?.id ? styles.currentTurn : ""}`}
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

          {/* Cards on table */}
          <div className={styles.centerTable}>
            {cardsOnTable.length > 0 ? (
              cardsOnTable.map((c) => (
                <CardButton
                  key={c.uid} // unique key
                  card={`${c.color}_${c.value}`}
                  disabled={!isMyTurn}
                  playable={isMyTurn}
                />
              ))
            ) : (
              <p className={styles.noCardsText}>(none yet)</p>
            )}
          </div>
        </div>

        {/* Player's hand */}
        <div className={styles.handSection}>
          <h3>Your Hand:</h3>
          <div className={styles.handCardsRow}>
            {myCards.length > 0 ? (
              myCards.map((c) => (
                <CardButton
                  key={c.uid} // unique key
                  card={`${c.color}_${c.value}`}
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
