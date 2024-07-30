import React, { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import tokenService from "../services/token.service";

const user = tokenService.getUser();

export default function Lobby() {
  const navigate = useNavigate();
  const { id } = useParams();
  const userId = user.id;
  const [creator, setCreator] = useState(null);
  const [playerId, setPlayerId] = useState("");
  const [codeGame, setCodeGame] = useState(null);
  const [isCompetitiveGame, setIsCompetitiveGame] = useState("");
  const [game, setGame] = useState();
  const [gameState, setGameState] = useState();
  const [isCreatorLoading, setIsCreatorLoading] = useState(true);
  const [playersList, setPlayersList] = useState([]);
  const [roundCreatedId, setRoundCreatedId] = useState(null);
  const [roundsList, setRoundsList] = useState([]);
  const [roundState, setRoundState] = useState();

  const KhakiBox = ({ children }) => (
    <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px" }}>
      {children}
    </div>
  );
  const jwt = tokenService.getLocalAccessToken();

  useEffect(() => {
    const fetchRounds = async () => {
      try {
        const response = await fetch(`/api/v1/games/${id}/rounds`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        const rondas = await response.json();
        setRoundsList(rondas);
        setRoundCreatedId(rondas[0].id)
      } catch (error) {
        console.error("Error fetching game:", error);
      }
    };

    fetchRounds();
  }, [id, jwt]);

  useEffect(() => {
    const fetchGameState = async () => {
      try {
        const response = await fetch(`/api/v1/games/${id}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        const game = await response.json();
        setGameState(game.state)
      } catch (error) {
        console.error("Error fetching game:", error);
      }
    };

    fetchGameState();

    const intervalId = setInterval(fetchGameState, 1000)
    if (gameState === "ONGOING") {
      navigate(`/game/${id}/round/${roundCreatedId}`)
    };
    return () => clearInterval(intervalId);
  }, [id, jwt, gameState, roundCreatedId, navigate]);

  useEffect(() => {
    const fetchData = async () => {
      const findPlayer = async () => {
        try {
          const findResponse = await fetch(`/api/v1/profile/player`, {
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
          });

          if (!findResponse.ok) {
            throw new Error(`Error: ${findResponse.status}`);
          }
          const player = await findResponse.json();
          setPlayerId(player.id);
        } catch (error) {
          console.error("Error buscando jugador actual:", error);
        }
      };


      const findIfIsCreator = async () => {
        try {
          const findCreatorResponse = await fetch(
            `/api/v1/gameProperties/creator/${id}/${playerId}`,
            {
              headers: {
                Authorization: `Bearer ${jwt}`,
              },
            }
          );

          if (!findCreatorResponse.ok) {
            throw new Error(`Error: ${findCreatorResponse.status}`);
          }
          const isCreator = await findCreatorResponse.json();
          setCreator(isCreator);
        } catch (error) {
          console.error("Error buscando jugador actual:", error);
        } finally {
          setIsCreatorLoading(false);
        }
      };

      const listPlayers = async () => {
        try {
          const findPlayersResponse = await fetch(`/api/v1/games/${id}/players`, {
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
          });

          if (!findPlayersResponse.ok) {
            throw new Error(`Error: ${findPlayersResponse.status}`);
          }
          const players = await findPlayersResponse.json();
          console.log(players);
          setPlayersList(players);
        } catch (error) {
          console.error("Error buscando jugadores:", error);
        }
      };

      try {
        const response = await fetch(`/api/v1/games/${id}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!response.ok) {
          throw new Error(`Error: ${response.status}`);
        }

        const game = await response.json();
        setCodeGame(game.gameCode);
        setIsCompetitiveGame(game.isCompetitive);
        setGame(game);
        setGameState(game.state);
        findPlayer();
        findIfIsCreator();
        listPlayers();
      } catch (error) {
        console.error("Error fetching game:", error);
      }
    };

    fetchData();

    const intervalId = setInterval(() => {
      fetchData();
    }, 1000);

    if (gameState === "ONGOING") {
      navigate(`/game/${id}/round/${roundCreatedId}`);
    }

    return () => clearInterval(intervalId);
  }, [id, playerId, jwt, gameState, roundCreatedId, navigate]);


  const handleStartGame = async () => {
    try {
      if (isCompetitiveGame && playersList.length !== 5) {
        alert("La partida competitiva debe tener 5 jugadores para empezar.");
      } else if (!isCompetitiveGame && playersList.length < 2) {
        alert("La partida debe tener al menos 2 jugadores para empezar.");
      } else {

      const roundPropertiesPromises = [];
      for (const round of roundsList) {
      
        for (const player of playersList) {
        const roundPropertiesPromise = (async () => {
        try {
        const createRoundPropertiesResponse = await fetch(`/api/v1/roundProperties`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
          },
          body: JSON.stringify({
            round: round,
            player: player,
          }),
        });

        if (!createRoundPropertiesResponse.ok) {
          throw new Error(`Error: ${createRoundPropertiesResponse.status}`);
        }

        const roundProperties = await createRoundPropertiesResponse.json();
        console.log(roundProperties);
      } catch (error) {
        console.error("Error al crear propiedades de la ronda:", error);
      }
    })();

    roundPropertiesPromises.push(roundPropertiesPromise);
    }
  }

    await Promise.all(roundPropertiesPromises);

        const startGameResponse = await fetch(`/api/v1/games/startGame/${id}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!startGameResponse.ok) {
          throw new Error(`Error: ${startGameResponse.status}`);
        }
        console.log("Empezando el juego...");
        setGameState('ONGOING');

        const startRoundResponse = await fetch(`/api/v1/startRound/rounds/${roundCreatedId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!startRoundResponse.ok) {
          throw new Error(`Error: ${startRoundResponse.status}`);
        }
        console.log("Empezando la ronda...");
        setRoundState('ONGOING');

        const initialDealResponsee = await fetch(`/api/v1/games/${id}/rounds/${roundCreatedId}/players/initialDeal`, {
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
      }
    } catch (error) {
      console.error("Error starting game:", error);
    }
  };

  return (
    <div
      className="create-game-container"
      style={{
        backgroundColor: "rgb(174, 97, 213)",
        height: "90.5vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <h1 style={{ marginTop: "50px" }}>
        Lobby de partida {isCompetitiveGame ? "competitiva" : "casual"}
      </h1>
      <h1 style={{ marginTop: "50px" }}>
        Codigo: {codeGame ? codeGame : "Cargando..."}
      </h1>
      <KhakiBox>
        <h2>Jugadores en el lobby:</h2>
        {playersList.map((player, index) => (
          <div>
            {index + 1}. {player.firstName} {player.lastName}{" "}
            {index + 1 === 1 && (
              <span style={{ color: "green" }}>Creador</span>
            )}
          </div>
        ))}
        {isCreatorLoading ? (
          <p>Cargando...</p>
        ) : (
          <>
            {creator && (
              <div>
                {isCompetitiveGame && playersList.length !== 5 ? (
                  <p style={{ color: "red" }}>
                    La partida competitiva debe tener al menos 5 jugadores para
                    empezar.
                  </p>
                ) : (
                  playersList.length === 1 ? (
                    <p style={{ color: "red" }}>
                      La partida debe tener al menos 2 jugadores para empezar.
                    </p>
                  ) : (
                    <button
                      onClick={handleStartGame}
                      style={{
                        backgroundColor: "khaki",
                        padding: "10px",
                        borderRadius: "5px",
                        cursor: "pointer",
                      }}
                    >
                      Empezar
                    </button>
                  )
                )}
              </div>
            )}
          </>
        )}
      </KhakiBox>
    </div>
  );
}