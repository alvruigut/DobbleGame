class cartasYPuntos {
    async fetchNewCardInfernoTower(gameId, roundId, playerId, jwt, fetchData) {
      try {
        await fetch(`/api/v1/games/${gameId}/rounds/${roundId}/players/${playerId}/infernoTower`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
          },
        });
        await fetchData();
      } catch (error) {
        console.error("Error fetching round data:", error);
      }
    }
  
    async fetchPointsInfernoTower(gameId, roundId, playerId, jwt) {
      try {
        await fetch(`/api/v1/games/${gameId}/rounds/${roundId}/pointsPlayerInfernoTower`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
          },
        });
      } catch (error) {
        console.error("Error fetching round data:", error);
      }
    }
  }
  
  export default new cartasYPuntos();