import React, { useEffect, useState } from 'react';
import { Button, Col, Container, Row, Table } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from '../../services/token.service';
import useFetchState from "../../util/useFetchState";

const user = tokenService.getUser();

export default function PlayerStadisticDuracion() {
  const jwt = tokenService.getLocalAccessToken();
  const userId = user.id;
  const[numGames, setNumGames] = useState(null);
  const [totalTimePlayed, setTotalTimePlayed] = useState(null);
  const [avgTimePlayed, setAvgTimePlayed] = useState(null);
  const [maxTimePlayed, setMaxTimePlayed] = useState(null);
  const [minTimePlayed, setMinTimePlayed] = useState(null);


  useEffect(() => {
    const obtenergamesPlayed = async () => {
      try {
        const response = await fetch(`/api/v1/games/numOfGames/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });        
        if (!response.ok) {
          throw new Error(`Error al obtener el tiempo promedio jugado para ${userId}`);
        }
        const result = await response.json();
        setNumGames(result);
      } catch (error) {
        console.error(error.message);
      }
    };
    obtenergamesPlayed();
  }, [jwt,userId]);
  useEffect(() => {
    const obtenerTotalTimePlayed = async () => {
      try {
        const response = await fetch(`/api/v1/games/timePlayed/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });        
        if (!response.ok) {
          throw new Error(`Error al obtener el tiempo promedio jugado para ${userId}`);
        }
        const result = await response.json();
        setTotalTimePlayed(result);
      } catch (error) {
        console.error(error.message);
      }
    };
    obtenerTotalTimePlayed();
  }, [jwt,userId]);

  useEffect(() => {
    const obtenerAvgTimePlayed = async () => {
      try {
        const response = await fetch(`/api/v1/games//avgTimePlayed/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        if (!response.ok) {
          throw new Error(`Error al obtener el tiempo promedio jugado para ${userId}`);
        }
        const result = await response.json();
        setAvgTimePlayed(result);
      } catch (error) {
        console.error(error.message);
      }
    }
    obtenerAvgTimePlayed();
  }, [jwt,userId]);

  useEffect(() => {
    const obtenerMaxTimePlayed = async () => {
      try {
        const response = await fetch(`/api/v1/games/maxTimePlayed/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        if (!response.ok) {
          throw new Error(`Error al obtener el tiempo promedio jugado para ${userId}`);
        }
        const result = await response.json();
        setMaxTimePlayed(result);
      } catch (error) {
        console.error(error.message);
      }
    }
    obtenerMaxTimePlayed();
  }, [jwt,userId]);

  useEffect(() => {
    const obtenerMinTimePlayed = async () => {
      try {
        const response = await fetch(`/api/v1/games/minTimePlayed/${userId}`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });
        if (!response.ok) {
          throw new Error(`Error al obtener el tiempo promedio jugado para ${userId}`);
        }
        const result = await response.json();
        setMinTimePlayed(result);
      } catch (error) {
        console.error(error.message);
      }
    }
    obtenerMinTimePlayed();
  }, [jwt,userId]);




  const KhakiBox = ({ children }) => (
    <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px", width: "60%", margin: "0 auto" }}>
      {children}
    </div>
);

return (
  <div className="auth-page-purple">
      <Container style={{ marginTop: '15px' }} fluid>
          <Row>
              <Col>
                  <h1 className="text-center">Estadísticas</h1>
                  <div className="auth-page-yellow">
                      <Button size="md" color="warning" tag={Link} to={`/stadistics/globals`}>
                          Estadísticas Globales
                      </Button>
                      <Button size="md" color="warning" tag={Link} to={`/stadistics/personal`}>
                          Estadísticas Personales
                      </Button>
                      <Button size="md" color="warning" tag={Link} to={`/stadistics/duracion`}>
                          Duración
                      </Button>
                      <Button size="md" color="warning" tag={Link} to={`/stadistics/logros`}>
                          Logros
                      </Button>
                  </div>
              </Col>
          </Row>
      </Container>
      <Container style={{ marginTop: '5px' }} fluid>
          <h1 className="text-center">Duración</h1>
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <KhakiBox>
                  <thead>
                      <tr>
                          <th width="20%">Partidas jugadas</th>
                          <th width="20%">Total de tiempo jugado</th>
                          <th width="20%">Tiempo medio jugado</th>
                          <th width="20%">Partida más lenta</th>
                          <th width="20%">Partida más rapida</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td>{numGames ? numGames : 'No hay resultado aún'}</td>
                          <td>{totalTimePlayed ? totalTimePlayed +" minutos": 'No hay resultado aún'}</td>
                          <td>{avgTimePlayed ? avgTimePlayed +" minutos": 'No hay resultado aún'}</td>
                          <td>{maxTimePlayed ? maxTimePlayed +" minutos": 'No hay resultado aún'}</td>
                          <td>{minTimePlayed ? minTimePlayed +" minutos": 'No hay resultado aún'}</td>
                      </tr>
                  </tbody>
                  </KhakiBox>
          </div>
      </Container>
  </div>
);
}