import React, { useState, useEffect, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import tokenService from "../services/token.service";
import torreInfernal from "./torreInfernal";
import { cardStyle, symbolContainerStyle, buttonStyle, imgStyle } from "./styles";
import FinishRoundModal from "../util/finishRoundModal";
import FinishGameModal from "../util/finishGameModal";


export default function GameScreen() {
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
  const [listPlayers, setAllPlayers] = useState([]);


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
      navigate(`/game/${gameId}/round/${r}/hotPotato`);
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

      if (isMounted) {
        if (ronda.isOnGoing === false) {
          let r = round.id + 1
          const isNextRoundAvailable = roundsList.some((roundItem) => roundItem.id === r);
          if (isNextRoundAvailable) {
            console.log("Comenzando la siguiente ronda...");
            setRoundState(true);
            navigate(`/game/${gameId}/round/${r}/hotPotato`);
          }
        } else {
          setTimeout(fetchRondaEstado, 1000);
        }
      }
    } catch (error) {
      console.error("Error fetching game:", error);
    }
  };

  fetchRondaEstado();

}, [gameId, jwt, navigate, round, roundId, roundsList]);

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

const fetchNewCard = async () => {
  try {
    if (player && player.id) {
      await torreInfernal.fetchNewCardInfernoTower(gameId, roundId, player.id, jwt, fetchData);
    } else {
      console.error("Player or player.id is undefined.");
    }
  } catch (error) {
    console.error("Error fetching round data:", error);
  }
};

const fetchPoints = async () => {
  try {
    if (player && player.id) {

      await torreInfernal.fetchPointsInfernoTower(gameId, roundId, player.id, jwt);

    } else {
      console.error("Player or player.id is undefined.");
    }
  } catch (error) {
    console.error("Error fetching round data:", error);
  }
};

const handleSymbolClick = async (symbol) => {
  if (centralDeck.length != 0) {
    // Verificar si el símbolo está en la baraja central
    const isSymbolInCentralCard = centralDeck[0].symbols.some((centralSymbol) => centralSymbol.id === symbol.id);

    if (isSymbolInCentralCard && centralDeck.length > 1) {
      await fetchNewCard();
    } else if (centralDeck.length === 1) {
      await fetchNewCard();
      await fetchPoints();
      setShowModalRound(true);
    }
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
  <div style={{ display: "flex", flexDirection: "row", justifyContent: "space-around", backgroundColor: "cyan", padding: "20px" }}>
    {playerDeck[0] && playerDeck[0].symbols && (
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center", gap: "10px" }}>
        <div style={{ textAlign: "center" }}>Carta del jugador</div>
        <div style={getCardStyle(violetRadius)}>
          <div style={getSymbolContainerStyle(yellowRadius)}>
            {playerDeck[0].symbols.map((symbol, symbolIndex) => {
              const { x, y } = getSymbolPosition(symbolIndex, playerDeck[0].symbols.length, yellowRadius);


              return (
                <button key={symbol.id} onClick={() => handleSymbolClick(symbol)} style={getButtonStyle(x, y, yellowRadius)}>
                  <img src={`/images/animals/${symbol.name.toUpperCase()}.png`} alt={symbol.name} style={getImgStyle} />
                </button>
              );
            })}
          </div>
        </div>
      </div>
    )}

    {centralDeck[0] && centralDeck[0].symbols && (
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center", gap: "10px" }}>
        <div style={{ textAlign: "center" }}>Carta central</div>
        <div style={getCardStyle(violetRadius)}>
          <div style={getSymbolContainerStyle(yellowRadius)}>
            {centralDeck[0].symbols.map((symbol, symbolIndex) => {
              const { x, y } = getSymbolPosition(symbolIndex, centralDeck[0].symbols.length, yellowRadius);

              return (
                <button key={symbol.id} style={getButtonStyle(x, y, yellowRadius)}>
                  <img src={`/images/animals/${symbol.name.toUpperCase()}.png`} alt={symbol.name} style={getImgStyle} />
                </button>
              );
            })}
          </div>
        </div>
      </div>
    )}

    {centralDeck.length === 0 && (
      <div>
        <text>Espera, por favor...</text>
        <text>¡No clicke en ninguna parte de la pantalla!</text>
      </div>
    )}

    <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end", justifyContent: "center", gap: "10px", marginLeft: "20px" }}>
      <div style={{ textAlign: "center" }}>Haga click sobre el símbolo de su carta</div>
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