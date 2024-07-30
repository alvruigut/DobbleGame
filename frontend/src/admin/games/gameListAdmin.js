import { useState } from "react";
import { Table } from "reactstrap";
import tokenService from "../../services/token.service";
import useFetchState from "../../util/useFetchState";
import getErrorModal from "../../util/getErrorModal";

const jwt = tokenService.getLocalAccessToken();

export default function GameListAdmin() {
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [games] = useFetchState(
        [],
        `/api/v1/games`,
        jwt,
        setMessage,
        setVisible
    );
    const [alerts] = useState([]);
    const [sortBy, setSortBy] = useState('id');

    const KhakiBox = ({ children }) => (
        <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px" }}>
          {children}
        </div>
    );

    const getStateText = (state) => {
        switch (state) {
            case 'UNSTARTED':
                return 'Sin empezar';
            case 'ONGOING':
                return 'En curso';
            case 'FINISHED':
                return 'Finalizada';
            default:
                return state;
        }
    };

    const sortById = () => {
        setSortBy('id');
    };

    const sortByState = () => {
        setSortBy('state');
    };

    const sortedGames = games.slice().sort((a, b) => {
        if (sortBy === 'id') {
            return a.id - b.id;
        } else if (sortBy === 'state') {
            const stateOrder = {
                UNSTARTED: 1,
                ONGOING: 2,
                FINISHED: 3,
            };
            if (stateOrder[a.state] === stateOrder[b.state]) {
                return a.id - b.id;
            }
            return stateOrder[a.state] - stateOrder[b.state];
        }
        return 0;
    });

    const gameList = sortedGames.map((game) => {
        return (
            <tr key={game.id}>
                <td>{game.id}</td>
                <td>{game.isCompetitive ? "✔️" : "❌"}</td>
                <td>{getStateText(game.state)}</td>
                <td>{game.timeLimit ?game.timeLimit : "❌" }</td>
                <td>{game.roundLimit ? game.roundLimit : "❌" }</td>
            </tr>
        );
    });

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <div className="admin-page-container" style={{ backgroundColor: 'rgb(174, 97, 213)', height: "99.5vh", display: "flex", flexDirection: "column", alignItems: "center" }}>
            <KhakiBox>
                <h1 className="text-center">Juegos</h1>
                {alerts.map((a) => a.alert)}
                {modal}
                <div>
                    <button onClick={sortById}>Ordenar por ID</button>
                    <button onClick={sortByState}>Ordenar por estado</button>
                    <Table aria-label="games" className="mt-4">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Competitivo</th>
                                <th>Estado</th>
                                <th>Tiempo limite</th>
                                <th>Limite de rondas</th>
                            </tr>
                        </thead>
                        <tbody>{gameList}</tbody>
                    </Table>
                </div>
            </KhakiBox>
        </div>
    );
}