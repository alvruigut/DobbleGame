import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import tokenService from "../services/token.service";

const KhakiBox = ({ children }) => (
  <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px" }}>
    {children}
  </div>
);

export default function CreateGame() {
  const [limitBy, setLimitBy] = useState("rounds");
  const [roundsLimit, setRoundsLimit] = useState(1);
  const [timeLimit, setTimeLimit] = useState("5");
  const [gameCreateId, setGameCreatedId] = useState(null);
  const [errorText, setErrorText] = useState("");

  const navigate = useNavigate();
  const jwt = tokenService.getLocalAccessToken();

  const handleCreateGame = async () => {
    try {
      let limite = null;
      if (limitBy === "rounds") {
        limite = true;
      } else {
        limite = false;
      }
      const createResponse = await fetch(`/api/v1/games`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify({
          limitedByRound: limite,
          roundLimit: limitBy === "rounds" ? roundsLimit : null,
          timeLimit: limitBy === "time" ? timeLimit : null,
          state: "UNSTARTED",
          isCompetitive: false,
        }),
      });

      let r;
      if (!createResponse.ok) {
        throw new Error(`Error: ${createResponse.status}`);
      }

      if (limitBy === "rounds") {
        r = roundsLimit-1;
      } else if(timeLimit === "5") {
        r = 2;
      } else {
        r = 4;
      }

      for (let i = 0; i < r; i++) {
        if (i % 2 === 0) {
          const createRoundResponse = await fetch(`/api/v1/rounds`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify({
              minigame: "TORREINFERNAL",
              isOnGoing: false,
            }),
          });

          if (!createRoundResponse.ok) {
            throw new Error(`Error: ${createRoundResponse.status}`);
          }
        } else {
          const createRoundResponse = await fetch(`/api/v1/rounds`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify({
              minigame: "PATATACALIENTE",
              isOnGoing: false,
            }),
          });

          if (!createRoundResponse.ok) {
            throw new Error(`Error: ${createRoundResponse.status}`);
          }

        }
      }


      const createdGame = await createResponse.json();
      setGameCreatedId(createdGame.id);
      try {
        const creatorResponse = await fetch(`/api/v1/gameProperties`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
          },
          body: JSON.stringify({
            isCreator: true,
            game: createdGame,
            gamePoints: 0,
          }),
        });

        if (!creatorResponse.ok) {
          throw new Error(`Error: ${creatorResponse.status}`);
        }
        const createdGameProperties = await creatorResponse.json();
        console.log("GameProperties creadas:", createdGameProperties);
      } catch (error) {
        console.error("Error creating GameProperties:", error);
        setErrorText("No se ha podido crear la partida");
      }
      if (createdGame.id == null) {
        setErrorText("Se ha producido un error");
      } else {
        navigate(`/lobby/${createdGame.id}`);
      }
    } catch (error) {
      console.error("Error creating game:", error);
      setErrorText("No se ha podido crear la partida");
    }
  };

  return (
    <div className="create-game-container" style={{ backgroundColor: 'rgb(174, 97, 213)', height: "90.5vh", display: "flex", flexDirection: "column", alignItems: "center" }}>
      <h1 style={{ marginTop: '50px' }}>Ajustes de Partida</h1>
      <img alt="logo" src="/dobble-logo2.png" style={{ height: 210, width: 210 }} />
      <div className="create-game-form-container" style={{ marginTop: "20px" }}>
        <KhakiBox>
          <form onSubmit={(e) => e.preventDefault()}>
            <label htmlFor="limitBy">Limitado por:</label>
            <select id="limitBy" name="limitBy" onChange={(e) => setLimitBy(e.target.value)}>
              <option value="rounds">Rondas</option>
              <option value="time">Tiempo</option>
            </select>

            {limitBy === "rounds" ? (
              <div>
                <label htmlFor="roundsLimit">Elige el numero de rondas:</label>
                <select id="roundsLimit" name="roundsLimit" onChange={(e) => setRoundsLimit(parseInt(e.target.value, 10))}>
                  <option value="1">1 Ronda</option>
                  <option value="2">2 Rondas</option>
                  <option value="3">3 Rondas</option>
                  <option value="4">4 Rondas</option>
                  <option value="5">5 Rondas</option>
                </select>
              </div>
            ) : (
              <div>
                <label htmlFor="timeLimit">Elige el tiempo limite:</label>
                <select id="timeLimit" name="timeLimit" onChange={(e) => setTimeLimit(parseInt(e.target.value, 10))}>
                  <option value="5">5 Minutos</option>
                  <option value="10">10 Minutos</option>
                </select>
              </div>
            )}
            <Link to={gameCreateId ? `/lobby/${gameCreateId}` : `/createGame`}>
              <button type="button" onClick={handleCreateGame}>Crear partida</button>
            </Link>
            {errorText && <p style={{ color: "red", marginTop: "5px" }}>{errorText}</p>}
          </form>
        </KhakiBox>
      </div>
    </div>
  );
}