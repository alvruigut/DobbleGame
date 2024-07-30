import { useState } from "react";
import { Link } from "react-router-dom";
import { Form, Input, Label } from "reactstrap";
import tokenService from "../../services/token.service";
import getErrorModal from "../../util/getErrorModal";
import getIdFromUrl from "../../util/getIdFromUrl";
import useFetchState from "../../util/useFetchState";

const user = tokenService.getUser();
const jwt = tokenService.getLocalAccessToken();

export default function PlayerEditAdmin() {
    const emptyItem = {
        id: "",
        firstName: "",
        lastName: "",
        email: "",
        telephone: "",
    };

    const id = getIdFromUrl(2);
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [player, setPlayer] = useFetchState(
        emptyItem,
        `/api/v1/players/${id}`,
        jwt,
        setMessage,
        setVisible,
        id
    );

    function handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        setPlayer({ ...player, [name]: value });
    }

    function handleSubmit(event) {
        event.preventDefault();

        fetch("/api/v1/players" + (player.id ? "/" + player.id : "" + `?userId=${user.id}`), {
            method: player.id ? "PUT" : "POST",
            headers: {
                Authorization: `Bearer ${jwt}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify(player),
        })
            .then((response) => response.json())
            .then((json) => {
                if (json.message) {
                    setMessage(json.message);
                    setVisible(true);
                } else window.location.href = "/players";
            })
            .catch((message) => alert(message));
    }

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <div className="auth-page-container">
            {<h2>{id !== "new" ? "Edit Player" : "Add Player"}</h2>}
            {modal}
            <div className="auth-form-container">
                <Form onSubmit={handleSubmit}>
                    
                    <div className="custom-form-input">
                        <Label for="firstName" className="custom-form-input-label">
                            Nombre
                        </Label>
                        <Input
                            type="text"
                            required
                            name="firstName"
                            id="firstName"
                            value={player.firstName || ""}
                            onChange={handleChange}
                            className="custom-input"
                        />
                    </div>

                    <div className="custom-form-input">
                        <Label for="lastName" className="custom-form-input-label">
                            Apellido
                        </Label>
                        <Input
                            type="text"
                            required
                            name="lastName"
                            id="lastName"
                            value={player.lastName || ""}
                            onChange={handleChange}
                            className="custom-input"
                        />
                    </div>

                    <div className="custom-form-input">
                        <Label for="email" className="custom-form-input-label">
                            Email
                        </Label>
                        <Input
                            type="text"
                            required
                            name="email"
                            id="email"
                            value={player.email || ""}
                            onChange={handleChange}
                            className="custom-input"
                        />
                    </div>

                    <div className="custom-form-input">
                        <Label for="email" className="custom-form-input-label">
                            Número de teléfono
                        </Label>
                        <Input
                            type="text"
                            required
                            name="telephone"
                            id="telephone"
                            value={player.telephone || ""}
                            onChange={handleChange}
                            className="custom-input"
                        />
                    </div>

                    <div className="custom-button-row">
                        <button className="auth-button">Guardar</button>
                        <Link
                            to={`/players`}
                            className="auth-button"
                            style={{ textDecoration: "none" }}
                        >
                            Cancelar
                        </Link>
                    </div>
                </Form>
            </div>
        </div>
    );
}