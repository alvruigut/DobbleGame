import React, { useState, useEffect, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import tokenService from "../services/token.service";
import torreInfernal from "./torreInfernal";
import { cardStyle, symbolContainerStyle, buttonStyle, imgStyle } from "./styles";
import FinishRoundModal from "../util/finishRoundModal";
import FinishGameModal from "../util/finishGameModal";
import hotPotato from "./hotPotato";

export default function HotPotato() {
  const navigate = useNavigate();
  const jwt = tokenService.getLocalAccessToken();
  const [centralDeck, setCentralDeck] = useState([]);
  const [playerDeck, setPlayerDeck] = useState([]);
  const [round, setRound] = useState({});
  const { gameId, roundId } = useParams();
  const [player, setPlayer] = useState();
  const [rp, setRp] = useState({});
  const [showModalRound, setShowModalRound] = useState(false);
  const [showModalGame, setShowModalGame] = useState(false);
  const [roundsList, setRoundsList] = useState([]);
  const [roundState, setRoundState] = useState(true);
  const [playerDeckClicked, setPlayerDeckClicked] = useState([]);
  const [allPlayerDecks, setAllPlayerDecks] = useState([]);
  const [listPlayers, setAllPlayers] = useState([]);
  const [miniroundIsFinish, setMiniroundIsFinish] = useState(false);

  const fetchData = useCallback(async () => {
    try {
      await torreInfernal.findAllPlayers(gameId, setAllPlayers, jwt);
      await torreInfernal.fetchRoundData(gameId, roundId, jwt, setRound, setCentralDeck, setRoundState);
      await torreInfernal.findPlayerAndGP(jwt, roundId, setRp, setPlayerDeck, setPlayer);
    } catch (error) {
      console.error("Error fetching game:", error);
    }
  }, [gameId, jwt, roundId]);

  useEffect(() => {
    fetchData();
  }, [jwt, gameId, roundId, rp, roundState, showModalRound, fetchData]);

  const fetchPlayerDecks = useCallback(async () => {
    try {
      const playerDeckPromises = listPlayers.map(async (player) => {
        const playerId = player.id;
        const deck = await torreInfernal.findPlayerDeck(jwt, roundId, playerId);
        return { playerId: player.id, playerName: player.firstName, deck };
      });
  
      const playerDecks = await Promise.all(playerDeckPromises);
      setAllPlayerDecks(playerDecks);
    } catch (error) {
      console.error("Error fetching player decks:", error);
    }
  },[jwt, listPlayers, roundId]);

  useEffect(() => {
    fetchPlayerDecks();
  }, [listPlayers, jwt, roundId, fetchPlayerDecks]);

  useEffect(() => {
    const fetchRounds = async () => {
      try {
        const response = await fetch(`/api/v1/games/${gameId}/rounds`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        const rondas = await response.json();
        setRoundsList(rondas);
      } catch (error) {
        console.error("Error fetching game:", error);
      }
    };

    fetchRounds();

  }, [gameId, jwt]);
  const handleFinishRound = async () => {
    const finishRoundResponse = await fetch(`/api/v1/finishRound/rounds/${roundId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
    });

    if (!finishRoundResponse.ok) {
      throw new Error(`Error: ${finishRoundResponse.status}`);
    }
    console.log("Finalizando la ronda...");
    setRoundState(false);

  }

  const handleNextRound = async () => {

    let r = round.id + 1
    const isNextRoundAvailable = roundsList.some((roundItem) => roundItem.id === r);

    if (isNextRoundAvailable) {
      const startRoundResponse = await fetch(`/api/v1/startRound/rounds/${r}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!startRoundResponse.ok) {
        throw new Error(`Error: ${startRoundResponse.status}`);
      }
      console.log("Comenzando la siguiente ronda...");
      setRoundState(true);

      const initialDealResponsee = await fetch(`/api/v1/games/${gameId}/rounds/${r}/players/initialDeal`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!initialDealResponsee.ok) {
        throw new Error(`Error: ${initialDealResponsee.status}`);
      }
      console.log("Repartiendo cartas...");
      navigate(`/game/${gameId}/round/${r}`);
    } else {
      setShowModalGame(true);
    }

    await handleFinishRound();

  }
  const handleFinishGame = async () => {
    try {   
    const finishGameResponse = await fetch(`/api/v1/games/finishGame/${gameId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
    });

    if (!finishGameResponse.ok) {
      throw new Error(`Error: ${finishGameResponse.status}`);
    }
    console.log("Finalizando el juego...");

    //SUMAR PUNTOS
    listPlayers.forEach(async p => {
      const sumPuntos = await fetch(`/api/v1/gameProperties/gamePoints/${gameId}/player/${p.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!sumPuntos.ok) {
        throw new Error(`Error: ${sumPuntos.status}`);
      }
      console.log("Sumando puntos");
    });

    const fetchWinners = await fetch(`/api/v1/games/isWinner/${gameId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
    });

    if (!fetchWinners.ok) {
      throw new Error(`Error: ${fetchWinners.status}`);
    }


  } catch (error) {
  }
  await handleFinishGame();

  navigate(`/game/${gameId}/winners`)
}

useEffect(() => {
  let isMounted = true;

  const fetchGameEstado = async () => {
    try {
      const response = await fetch(`/api/v1/games/${gameId}`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Error en la solicitud: ${response.status} - ${response.statusText}`);
      }

      const game = await response.json();

      if (isMounted) {
        if (game.state === "FINISHED") {
          console.log("Llendo a ver los ganadores...");
          setRoundState(true);
          navigate(`/game/${gameId}/winners`);
        }
      } else {
        setTimeout(fetchGameEstado, 1000);
      }
    } catch (error) {
      console.error("Error fetching game:", error);
    }
  };

  fetchGameEstado();

}, [gameId, jwt, navigate, round, roundId, roundsList]);

  const fetchRondaEstado = async () => {
    try {
      const response = await fetch(`/api/v1/games/${gameId}/rounds/${roundId}`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Error en la solicitud: ${response.status} - ${response.statusText}`);
      }

      const ronda = await response.json();

      if (ronda.isOnGoing === false) {
        let r = round.id + 1
        const isNextRoundAvailable = roundsList.some((roundItem) => roundItem.id === r);
        if (isNextRoundAvailable) {
          console.log("Comenzando la siguiente ronda...");
          setRoundState(true);
          navigate(`/game/${gameId}/round/${r}`);
        }
      } else {
        setTimeout(fetchRondaEstado, 1000);
      }
    } catch (error) {
      console.error("Error fetching game:", error);
    }
  };

  fetchRondaEstado();

  const fetchNextDealHotPotato = async () =>{
    try {
      if (player && player.id) {
          await hotPotato.fetchNextDealHotPotato(gameId, roundId, jwt, fetchData);
      } else {
        console.error("Player or player.id is undefined.");
      }
    } catch (error) {
      console.error("Error fetching round data:", error);
    }
  }
  
  const fetchPointsHotPotato = async () =>{
    try {
      if (player && player.id) {
        await hotPotato.fetchPointsHotPotato(gameId, roundId, jwt);
      } else {
        console.error("Player or player.id is undefined.");
      }
    } catch (error) {
      console.error("Error fetching round data:", error);
    }
  }

  const fetchPointsCountHotPotato = async() => {
    try {
      if (player && player.id) {
        await hotPotato.fetchPointsCountHotPotato(gameId, roundId, jwt);
      } else {
        console.error("Player or player.id is undefined.");
      }
    } catch (error) {
      console.error("Error fetching round data:", error);
    }
  }

  function fetchUpdateHotPotato(playerId2) {
    try {
      hotPotato.fetchUpdateHotPotato(gameId, roundId, player.id, playerId2, jwt)
    } catch (error) {
      console.error("Error updating round data:", error);
    }
  }

  function changeMiniroundState() {
    setMiniroundIsFinish(allPlayerDecks.filter(playerDeck => playerDeck.deck.length > 0).length === 1);
  }

  useEffect(() => {
    changeMiniroundState();
  }, [allPlayerDecks])

  useEffect(() => {
    const handleIsFinishMiniround = async () => {
      if (miniroundIsFinish === true && centralDeck.length >= listPlayers.length) {
        await fetchPointsHotPotato();
        await fetchNextDealHotPotato();
      } else if (miniroundIsFinish === true) {
        await fetchPointsHotPotato();
        await fetchPointsCountHotPotato();
        setShowModalRound(true);
      }
    };
  
    if (round && round.isOnGoing) {
      handleIsFinishMiniround();
    }
  }, [miniroundIsFinish]);
  

  const handlePlayerCardClick = (clickedPlayerDeck) => {
    const matchingPlayerDeck = allPlayerDecks.find((playerDeck) =>
      playerDeck.deck===clickedPlayerDeck.deck);
      // Almacenar el mazo y el id del jugador sobre el que se ha hecho clic
      setPlayerDeckClicked({
        playerId: matchingPlayerDeck.playerId,
        playerDeck: matchingPlayerDeck.deck,
      });
  }; 

  
  const handleSymbolClick = async (symbol) => {
    const isSymbolInPlayerDeck =
    playerDeck &&
    playerDeck.length > 0 &&
    playerDeck[0].symbols &&
    playerDeck[0].symbols.some((playerSymbol) => playerSymbol.id === symbol.id);
  console.log(isSymbolInPlayerDeck);
    const playerId2 =
    playerDeckClicked &&
    allPlayerDecks.find((playerDeck) => playerDeck.playerId === playerDeckClicked.playerId)?.playerId;

    if(playerId2!=player.id){
      if(isSymbolInPlayerDeck){
        fetchUpdateHotPotato(playerId2);
      }
      }else{
        alert("No puedes hacer click sobre tu propia carta.");
      }
  };


  const getSymbolPosition = (index, totalSymbols, radius) => {
    const angle = (index / totalSymbols) * (2 * Math.PI);
    const x = radius * Math.cos(angle) * 0.7;
    const y = radius * Math.sin(angle) * 0.7;

    return { x, y };
  };

  const getCardStyle = () => cardStyle(violetRadius);
  const getSymbolContainerStyle = () => symbolContainerStyle(yellowRadius);
  const getButtonStyle = (x, y) => buttonStyle(x, y, yellowRadius);
  const getImgStyle = imgStyle;

  const violetRadius = 250 / 2;
  const yellowRadius = 220 / 2;

  return (
    <div style={{ display: 'flex', gap: '20px', backgroundColor: "cyan",}}>
      {allPlayerDecks.map((deckOfPlayer, index) => (
        <div key={index} onClick={() => handlePlayerCardClick(deckOfPlayer)}>
          <div>
            <h2>{deckOfPlayer.playerName}</h2>
            {deckOfPlayer.deck.map((card) => (
              <div key={card.id} style={getCardStyle(violetRadius)}>
                <div style={getSymbolContainerStyle(yellowRadius)}>
                  {card.symbols.map((symbol, symbolIndex) => {
                    const { x, y } = getSymbolPosition(symbolIndex, card.symbols.length, yellowRadius);
    
                    return (
                      <button
                        key={symbol.id}
                        onClick={() => {
                          handleSymbolClick(symbol)
                        }}
                        style={getButtonStyle(x, y, yellowRadius)}
                      >
                        <img
                          src={`/images/animals/${symbol.name.toUpperCase()}.png`}
                          alt={symbol.name}
                          style={getImgStyle}
                        />
                      </button>
                    );
                  })}
                </div>
              </div>
            ))}
          </div>
        </div>
      ))}
          {miniroundIsFinish && (
      <div>
        <text>Preparando próximo reparto...</text>
      </div>
    )}

    <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end", justifyContent: "center", gap: "10px", marginLeft: "20px" }}>
      <div style={{ textAlign: "center" }}>Haga doble click sobre el símbolo de la carta de los otros jugadores</div>
    </div>

      <FinishRoundModal
        isOpen={showModalRound}
        onRequestClose={() => setShowModalRound(false)}
        onNextRound={handleNextRound}
      />
      {showModalGame && (
        <FinishGameModal
          isOpen={showModalGame}
          onRequestClose={() => setShowModalGame(false)}
          onReturnToMainScreen={handleFinishGame}

        />
      )}
    </div>
  );
}