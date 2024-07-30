import React, { useState, useEffect, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import tokenService from "../services/token.service";



export default function WinnersScreen() {
  const navigate = useNavigate();
  const jwt = tokenService.getLocalAccessToken();
  const {gameId} = useParams();
  const [gp, setGp] = useState([]);

  const KhakiBox = ({ children }) => (
    <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px" }}>
      {children}
    </div>
  );

  useEffect(() => {
    const fetchGP = async () => {
      try {
        const response = await fetch(`/api/v1/gameProperties/games/${gameId}/id`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!response.ok) {
          throw new Error(`Error en la solicitud: ${response.status} - ${response.statusText}`);
        }

        const gameProperties = await response.json();
        setGp(gameProperties);
        console.log(gameProperties);
      } catch (error) {
        console.error("Error fetching game:", error);
      }
    };

    fetchGP();
  }, [gameId, jwt]);

  const handleGoToPlayerPage = () => {
    navigate("/player");
  };
  
 const renderWinnersList = useCallback(() => {
    console.log(gp);
    if (!gp || gp.length === 0) {
      return <p>Cargando...</p>;
    }

    const sortedPlayers = [...gp]
      .filter((gameProperty) => gameProperty.player) 
      .sort((a, b) => b.gamePoints - a.gamePoints);

    return (
      <div>
        {sortedPlayers.map((gameProperty, index) => (
          <div key={gameProperty.player.id}>
            {index + 1}. 
            {gameProperty.player.firstName} - Puntos: {gameProperty.gamePoints}
            {gameProperty.isGameWinner===true ? <strong> - Winner </strong> : ""}
          </div>
        ))}
      </div>
    );
  }, [gp]);

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
      <KhakiBox>
        {renderWinnersList()}
      <button onClick={handleGoToPlayerPage}>Pantalla de inicio</button>
      </KhakiBox>
    </div>
  );
}
