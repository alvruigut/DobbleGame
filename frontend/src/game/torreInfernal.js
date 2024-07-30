import cartasYPuntos from "./cartasYPuntos";
class torreInfernal{
  async fetchRoundData(gameId, roundId, jwt, setRound, setCentralDeck, setRoundState, setMinigame) {
    try {
      const response = await fetch(`/api/v1/games/${gameId}/rounds/${roundId}`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Error: ${response.status}`);
      }

      const roundData = await response.json();
      setRound(roundData);

      const cd = roundData.centralDeck;
      setCentralDeck(cd);

      const state = roundData.isOnGoing;
      setRoundState(state);
    } catch (error) {
      console.error("Error fetching round data:", error);
    }
  }

  async findAllPlayers(gameId, setAllPlayers, jwt){
    try {
      const findPlayersResponse = await fetch(`/api/v1/games/${gameId}/players`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!findPlayersResponse.ok) {
        throw new Error(`Error: ${findPlayersResponse.status}`);
      }
      const players = await findPlayersResponse.json();
      setAllPlayers(players);
    } catch (error) {
      console.error("Error buscando jugadores:", error);
    }
  }

  async findPlayerAndGP(jwt, roundId, setRp, setPlayerDeck,setPlayer) {
    try {
      const findResponse = await fetch(`/api/v1/profile/player`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      if (!findResponse.ok) {
        throw new Error(`Error: ${findResponse.status}`);
      }

      const p = await findResponse.json();
      setPlayer(p);
      try {
        const findResponse = await fetch(`/api/v1/rounds/${roundId}/players/${p.id}/roundProperties`, {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        });

        if (!findResponse.ok) {
          throw new Error(`Error: ${findResponse.status}`);
        }

        const roundProp = await findResponse.json();
        setRp(roundProp);
        if (roundProp && roundProp.cards && roundProp.cards[0] && roundProp.cards[0].symbols) {
          const cards = roundProp.cards;
          setPlayerDeck(cards);
        }
      } catch (error) {
        console.error("Error buscando propiedades de la ronda para el jugador actual:", error);
      }
    } catch (error) {
      console.error("Error buscando jugador actual:", error);
    }
  }

  async findPlayerDeck(jwt, roundId, playerId) {
    try {
      const findResponse = await fetch(`/api/v1/rounds/${roundId}/players/${playerId}/roundProperties`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });
  
      if (!findResponse.ok) {
        throw new Error(`Error: ${findResponse.status}`);
      }
  
      const roundProp = await findResponse.json();
      return roundProp.cards || [];
    } catch (error) {
      console.error(`Error finding deck for player ${playerId}: ${error.message}`);
      return [];
    }
  }
  

  async fetchNewCardInfernoTower(gameId, roundId, playerId, jwt, fetchData) {
    await cartasYPuntos.fetchNewCardInfernoTower(gameId, roundId, playerId, jwt, fetchData);
  }

  async fetchPointsInfernoTower(gameId, roundId, playerId, jwt) {
    await cartasYPuntos.fetchPointsInfernoTower(gameId, roundId, playerId, jwt);
  }
}

export default new torreInfernal();