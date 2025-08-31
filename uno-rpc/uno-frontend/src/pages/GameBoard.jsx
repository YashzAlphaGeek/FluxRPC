import React, { useEffect, useState } from "react";
import { subscribeGameState, playCard } from "../services/UnoService";
import styles from "../styles/GameBoard.module.css";

const GameBoard = ({ gameId, playerId, playerName }) => {
  const [players, setPlayers] = useState([]); // [{id, name, cards}]
  const [currentPlayer, setCurrentPlayer] = useState("");
  const [cardsOnTable, setCardsOnTable] = useState([]);
  const [myCards, setMyCards] = useState([]);

  useEffect(() => {
    if (!gameId) return;

    const stream = subscribeGameState(
      gameId,
      (resp) => {
        const state = resp.toObject();
        console.log("GameState response:", state);

        // Store players with card counts
        if (state.playersList && state.playersList.length > 0) {
          setPlayers(
            state.playersList.map(p => ({
              id: p.id,
              name: p.name || p.id,
              cards: p.cards || []
            }))
          );
        }

        setCurrentPlayer(state.currentplayerid || "");
        setCardsOnTable(state.cardsontableList || []);

        // Your hand
        const me = state.playersList?.find(p => p.id === playerId);
        setMyCards(me?.cards || []);
      },
      console.error,
      () => console.log("GameState stream ended")
    );

    return () => stream.cancel();
  }, [gameId, playerId]);

  const isMyTurn = currentPlayer === playerId;

  const handlePlayCard = (card) => {
    if (!isMyTurn) {
      alert("Not your turn!");
      return;
    }
    playCard(gameId, playerId, card).catch(console.error);
  };

  return (
    <div className={styles.boardContainer}>
      <h3>Players:</h3>
      <ul>
        {players.map((p) => (
          <li key={p.id}>
            {p.name} {p.id === currentPlayer && "(current turn)"} — {p.id === playerId ? `${p.cards.length} cards (you)` : `${p.cards.length} cards`}
          </li>
        ))}
      </ul>

      <h3>Cards on Table:</h3>
      <ul className={styles.cardsList}>
        {cardsOnTable.length > 0
          ? cardsOnTable.map((c, i) => <li key={i}>{c}</li>)
          : <li>(none yet)</li>}
      </ul>

      <h3>Your Hand:</h3>
      <div className={styles.cardButtons}>
        {myCards.length > 0
          ? myCards.map((c, i) => (
              <button key={i} onClick={() => handlePlayCard(c)} disabled={!isMyTurn}>
                {c}
              </button>
            ))
          : <p>(no cards yet)</p>}
      </div>
    </div>
  );
};

export default GameBoard;
