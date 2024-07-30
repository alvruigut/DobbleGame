import { useState } from "react";
import { Link } from "react-router-dom";
import { Button, ButtonGroup, Table, Container } from "reactstrap";
import tokenService from "../../services/token.service";
import useFetchState from "../../util/useFetchState";
import getErrorModal from "../../util/getErrorModal";
import deleteFromList from "../../util/deleteFromList";

const jwt = tokenService.getLocalAccessToken();

export default function PlayerListAdmin() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [players, setPlayers] = useFetchState(
    [],
    `/api/v1/players`,
    jwt,
    setMessage,
    setVisible
  );
  const [alerts, setAlerts] = useState([]);

  const playerList = players.map((player) => {
    return (
      <tr key={player.id}>
        <td width="20%">{player.firstName}</td>
        <td width="20%">{player.lastName}</td>
        <td width="20%">{player.email}</td>
        <td width="20%">{player.telephone}</td>
        <td>
          <ButtonGroup>
            <Button
              size="sm"
              color="primary"
              aria-label={"edit-" + player.id}
              tag={Link}
              to={"/players/" + player.id}
            >
              Editar
            </Button>
            <Button
              size="sm"
              color="danger"
              aria-label={"delete-" + player.id}
              onClick={() =>
                deleteFromList(
                  `/api/v1/players/${player.id}`,
                  player.id,
                  [players, setPlayers],
                  [alerts, setAlerts],
                  setMessage,
                  setVisible
                )
              }
            >
              Eliminar
            </Button>
          </ButtonGroup>
        </td>
      </tr>
    )
  });

  const modal = getErrorModal(setVisible, visible, message);
  const KhakiBox = ({ children }) => (
    <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "30px", width: "69%", margin: "15% auto", transform: "translate(80%, -20%)" }}>
      {children}
    </div>
);

  return (
    <div className="auth-page-purple">
      <Container style={{ marginTop: "50px" }} fluid>
      <h1 className="text-center">Jugadores</h1>
      </Container>
      {alerts.map((a) => a.alert)}
      {modal}
      <div>
      <KhakiBox>
                  <thead>
            <tr>
              <th width="20%">Nombre</th>
              <th width="20%">Apellido</th>
              <th width="20%">Email</th>
              <th width="20%">Número de teléfono</th>
            </tr>
          </thead>
          <tbody>{playerList}</tbody>
          </KhakiBox>
          </div>
    </div>
  );
}