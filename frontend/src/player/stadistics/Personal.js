import {
    Button,
    ButtonGroup,
    Col,
    Container,
    Input,
    Row,
    Table,
  } from "reactstrap";
  import { Link } from "react-router-dom";
  import { useEffect, useState } from "react";
  import tokenService from '../../services/token.service';

  import useFetchState from "../../util/useFetchState";

  const user = tokenService.getUser();
  
export default function PlayerStadisticPersonal(){
  const jwt = tokenService.getLocalAccessToken();
  const userId = user.id;


  const [gamesF, gr] = useState(null);
  const [gamesRanked, gkr] = useState(null);
  const [gamesRankedWon, grwn] = useState(null);
  const [gamesFriendWon, gfwn] = useState(null);
  const [gamesList, setGames] = useState(null);
  const [playerList, setPlayers] = useState(null);




  useEffect(() => {
    const numGamesF = async () => {
      try {
        const response = await fetch(`/api/v1/games/numOfNotCompetitiveGames/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });        
        if (!response.ok) {
          throw new Error(`Error   ${userId}`);
        }
        const result = await response.json();
        gr(result);
      } catch (error) {
        console.error(error.message);
      }
    };
    numGamesF();
  }, [jwt,userId]);

  useEffect(() => {
    const numGamesR = async () => {
      try {
        const response = await fetch(`/api/v1/games/numOfCompetitiveGames/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });        
        if (!response.ok) {
          throw new Error(`Error   ${userId}`);
        }
        const result = await response.json();
        gkr(result);
      } catch (error) {
        console.error(error.message);
      }
    };
    numGamesR();
  }, [jwt,userId]);

  useEffect(() => {
    const numRankedWon = async () => {
      try {
        const response = await fetch(`/api/v1/games/numOfCompetitiveGamesWon/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });        
        if (!response.ok) {
          throw new Error(`Error   ${userId}`);
        }
        const result = await response.json();
        grwn(result);
      } catch (error) {
        console.error(error.message);
      }
    };
    numRankedWon();
  }, [jwt,userId]);

  useEffect(() => {
    const numFWon = async () => {
      try {
        const response = await fetch(`/api/v1/games/numOfNotCompetitiveGamesWon/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });        
        if (!response.ok) {
          throw new Error(`Error   ${userId}`);
        }
        const result = await response.json();
        gfwn(result);
      } catch (error) {
        console.error(error.message);
      }
    };
    numFWon();
  }, [jwt,userId]);

  //Mostrar Lista de partidas
  useEffect(() => {
    const listGames = async () => {
      try {
        const response = await fetch(`/api/v1/games/list/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        if (!response.ok) {
          throw new Error(`Error   ${userId}`);
        }
        const result = await response.json();
        setGames(result);
      } catch (error) {
        console.error(error.message);
      }
    };  
    listGames();
  }, [jwt, userId]);
  





  const KhakiBox = ({ children }) => (
    <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px", width: "80%", margin: "0 auto" }}>
      {children}
    </div>
);

    return(
        <div className="auth-page-purple ">
            <Container style={{ marginTop: "15px" }} fluid>
      <h1 className="text-center">Estadísticas</h1>
      <div className="auth-page-yellow">
      <Button
        size="md"
        color= "warning"
        tag={Link}
        to={`/stadistics/globals`}
        >
          Estadísticas Globales
        </Button>
        <Button
          size="md"
          color= "warning"
          tag={Link}
          to={`/stadistics/personal`}
          >
            Estadísticas Personales
          </Button> 
          <Button
          size="md"
          color= "warning"
          tag={Link}
          to={`/stadistics/duracion`}
          >
            Duración
          </Button>
          <Button
          size="md"
          color= "warning"
          tag={Link}
          to={`/stadistics/logros`}
          >
            Logros
          </Button> 
          </div>      
        </Container>
        <Container style={{ marginTop: "5px" }} fluid>
            <h1 className="text-center">Estadísticas Personales</h1>
        </Container>



        <KhakiBox>
                  <thead>
                      <tr>
                          <th width="20%">Número de partidas competitivas jugadas</th>
                          <th width="20%">Número de partidas amistosas jugadas</th>
                          <th width="20%">Partidas Competitiva Ganadas</th>
                          <th width="20%">Partidas Amistosas Ganadas</th>

                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td>{gamesRanked ? gamesRanked : 0}</td>
                          <td>{gamesF ? gamesF : 0}</td>
                          <td>{gamesRankedWon ? gamesRankedWon : 0}</td>
                          <td>{gamesFriendWon ? gamesFriendWon : 0}</td>
                      </tr>
                  </tbody>
              </KhakiBox>

              <Container style={{ marginTop: "5px" }} fluid>
            <h1 className="text-center">Listado De Partidas</h1>
        </Container>
        <KhakiBox>
        <thead>
                      <tr>
                          <th width="20%"></th>
                      </tr>
                  </thead>
                  <tbody>
      {gamesList ? (
        gamesList.map((game, index) => (
          <tr key={index}>
            <td>
              {/* Mostrar propiedades específicas del objeto game */}
              ID: {game.id},   Comienzo: {game.startDate}, Final: {game.finishDateTime}, Tipo: {game.isCompetitive ? "Competitiva" : "Amistosa"}         
            </td>
          </tr>
        ))
      ) : (
        <tr>
          <td>No se han jugado partidas todavía</td>
        </tr>
      )}
    </tbody>
        </KhakiBox>

</div>
);
}