import React, { useState } from "react";
import { Link, useNavigate} from "react-router-dom";
import tokenService from "../services/token.service";

const KhakiBox = ({ children }) => (
  <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px", marginBottom: "15px" }}>
    {children}
  </div>
);

export default function PlayerIndex() {
  const navigate = useNavigate();
  const [codigoPartida, setCodigoPartida] = useState("");
  const [gameIdResult, setGameIdResult] = useState(null);
  const [errorText, setErrorText] = useState("");
  const [gameCreateId,setGameCreatedId] = useState(null);

  const handleCreateGame = async () => {
    const jwt = tokenService.getLocalAccessToken();
    try {
      const createResponse = await fetch(`/api/v1/games`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify({
          limitedByRound: true,
          roundLimit: 5,
          timeLimit: null,
          state: "UNSTARTED",
          isCompetitive: true,
        }),
      });

      if (!createResponse.ok) {
        throw new Error(`Error: ${createResponse.status}`);
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
      if( createdGame.id==null){
        setErrorText("Se ha producido un error"); 
      }else{
      navigate(`/lobby/${createdGame.id}`);
      }
    } catch (error) {
      console.error("Error creating game:", error);
      setErrorText("No se ha podido crear la partida");
    }
  };

  const handleBuscarPartida = async () => {
    try {
      const jwt = tokenService.getLocalAccessToken();

      const response = await fetch(`/api/v1/games/gameCode/${codigoPartida}`, {
        headers: {
          Authorization: `Bearer ${jwt}`, 
        },
      });

      if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
      }

      const game = await response.json();

      const stateResponse = await fetch(`/api/v1/games/state_by/${game.id}`, {
        headers: {
          Authorization: `Bearer ${jwt}`, 
        },
      });

      if (!stateResponse.ok) {
        throw new Error(`Error: ${stateResponse.status}`);
      }

      const gameState = await stateResponse.json();
        try {
          const notCreatorResponse = await fetch(`/api/v1/gameProperties`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify({
              isCreator: false,
              game: game,
              gamePoints: 0,
            }),
          });
  
          if (!notCreatorResponse.ok) {
            throw new Error(`Error: ${notCreatorResponse.status}`);
          }
        const createdGameProperties = await notCreatorResponse.json();
        console.log("GameProperties creadas:", createdGameProperties);
        } catch (error) {
          console.error("Error creating GameProperties:", error);
          setErrorText("No se ha podido crear la partida");
        }

      if (gameState === 'UNSTARTED') {
        setGameIdResult(game.id);
        setErrorText("Partida encontrada, redirigiendo..."); 
        navigate(`/lobby/${game.id}`);
      } else if(gameState === 'ONGOING') {
        setGameIdResult(null);
        setErrorText("No puedes unirte a esta partida porque ya está empezada.");
      }else{
        setGameIdResult(null);
        setErrorText("No puedes unirte a esta partida porque ya está terminada.");
      }
    } catch (error) {
      console.error("Error fetching game:", error);
      setGameIdResult(null);
      setErrorText("No se ha encontrado la partida");
    }
  };

  return (
    <div className="index-player-page-container" style={{ backgroundColor: 'rgb(174, 97, 213)', height: "99.5vh", display: "flex", flexDirection: "column", alignItems: "center" }}>
        <img alt="logo" src="/dobble-logo2.png" style={{ height: 250, width: 250 }} />
      <div className="index-player-form-container" style={{marginTop: '35px'}}>
        <KhakiBox>
          <form onSubmit={(e) => e.preventDefault()}>
            <label htmlFor="codigoPartida">Introduce el código de la partida:</label>
            <input
              type="text"
              id="codigoPartida"
              value={codigoPartida}
              onChange={(e) => setCodigoPartida(e.target.value)}
              placeholder="Codigo de la partida"
            />
            <Link to={gameIdResult ? `/lobby/${gameIdResult}` : "/player"}>
              <button type="submit" onClick={handleBuscarPartida}>Buscar Partida</button>
            </Link>
            {errorText && <p style={{ color: "red", marginTop: "5px" }}>{errorText}</p>}
          </form>
        </KhakiBox>
      </div>
      <div className="index-player-form-container" style={{ display: "flex", justifyContent: "center" }}>
        <KhakiBox>
          <Link to="/createGame">
            <button style={{paddingBottom: "10px" }} >Crear Partida Casual</button>
          </Link>
        </KhakiBox>
      </div>
      <div className="index-player-form-container" style={{ display: "flex", justifyContent: "center" }}>
        <KhakiBox>
        <Link to={gameCreateId? `/lobby/${gameCreateId}` : `/player`}>
            <button style={{paddingBottom: "10px" }} onClick={handleCreateGame}>Crear Partida Competitiva</button>
          </Link>
        </KhakiBox>
      </div>
    </div>
  );
}