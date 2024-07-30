import "../../static/css/player/editButton.css";
import "../../static/css/player/editProfile.css";
import tokenService from "../../services/token.service";
import getIdFromUrl from "../../util/getIdFromUrl";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";
import {PlayerEditInputs} from "./form/playerEditFormInputs";
import FormGenerator from "../../components/formGenerator/formGenerator";
import { useState, useEffect, useRef } from "react";
import {useNavigate} from "react-router-dom";

const user = tokenService.getUser();
const jwt = tokenService.getLocalAccessToken();

export default function EditPlayer() {
  const userId = user.id;
  const navigator = useNavigate();

  const emptyItem = {
    id: "",
    firstName: "",
    lastName: "",
    email: "",
    telephone: "",
    username: "",
    password: ""
  };

  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [player, setPlayer] = useFetchState(
    emptyItem,
    `/api/v1/profile`,
    jwt,
    setMessage,
    setVisible,
    userId
  );

  const playerId = player.id;
  const [dataLoaded, setDataLoaded] = useState(false);

  const editPlayerFormRef = useRef(null);

  function handleSubmit({ values }) {
    if (!editPlayerFormRef.current.validate()) return;

    if (userId !== "new") {
      values.id = playerId
      fetch(`/api/v1/profile`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify(values),
      })
      .then((res) => {
        if (res.status === 200) {
          navigator("/player");
        }
      })
      .catch((err) => {
        setMessage(err.message);
      });;
    } else {
      fetch(`/api/v1/players`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwt}`,
        },
        body: JSON.stringify(values),
      })
      .then((res) => {
        if (res.status === 201) {
          navigator("/player");
        }
      })
      .catch((err) => {
        setMessage(err.message);
      });
    }
  }

  useEffect(() => {
    if (player.id !== "") {
        PlayerEditInputs.forEach((input) => {
        input.defaultValue = player[input.name];
        setDataLoaded(true);
      });
    } else {
        PlayerEditInputs.forEach((input) => {
        input.defaultValue = "";
      });
    }
  }, [player]);

  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div className="edit-profile-container">
      {<h2>{userId !== "new" ? "Editar Jugador" : "Añadir Jugador"}</h2>}
      {modal}
      <div className="edit-form-container">
        {dataLoaded ? (
          <FormGenerator
            ref={editPlayerFormRef}
            inputs={PlayerEditInputs}
            onSubmit={handleSubmit}
            buttonText="Editar"
            buttonClassName="edit-button"
          />
        ) : (
          <FormGenerator
            ref={editPlayerFormRef}
            inputs={PlayerEditInputs}
            onSubmit={handleSubmit}
            buttonText="Editar"
            buttonClassName="edit-button"
          />
        )}
      </div>
    </div>
  );
}
