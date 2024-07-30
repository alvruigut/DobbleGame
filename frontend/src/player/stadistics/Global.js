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


  const user = tokenService.getUser();

export default function PlayerStadisticGlobal(){
  const jwt = tokenService.getLocalAccessToken();
  const [player,setP] = useState(null);



  useEffect(() => {
    const playerT = async () => {
      try {
        const response = await fetch(`/api/v1/games/player/victoriesRanked`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });        
        if (!response.ok) {
          throw new Error(`Error `);
        }
        const result = await response.json();
        setP(result);
      } catch (error) {
        console.error(error.message);
      }
    };
    playerT();
  }, [jwt]);

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
            <h1 className="text-center"> Estadísticas Globales</h1>
        </Container>
        <KhakiBox>
            <thead>
              <tr>
                <th width="20%">Ranking</th>
                <th width="20%">Jugadores</th>
                <th width="20%">Victorias Competitivo</th>
              </tr>
            </thead>
            <tbody>
              {/* Mapear sobre las claves del objeto player y mostrar los valores */}
              {Object.keys(player || {}).map((key, index) => (
                <tr key={index}>
                  <td>{"#"+(index+1)}</td>
                  <td>{key}</td>
                  <td>{player[key]}</td>
                </tr>
              ))}
            </tbody>
            </KhakiBox>

  </div>
);
}