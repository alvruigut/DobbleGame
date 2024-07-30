class hotPotato{
    async fetchNextDealHotPotato(gameId, roundId, jwt, fetchData) {
        try {
          await fetch(`/api/v1/games/${gameId}/rounds/${roundId}/players/nextDeal`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
          });
          await fetchData();
        } catch (error) {
          console.error("Error fetching new card in hot potato:", error);
        }
      }

      async fetchUpdateHotPotato(gameId, roundId, playerId1, playerId2, jwt){
        try {
          await fetch(`/api/v1/games/${gameId}/rounds/${roundId}/players/${playerId1}/${playerId2}/hotPotato`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
          });
        } catch (error) {
          console.error("Error fetching new card in hot potato:", error);
        }
      }

      async fetchPointsHotPotato(gameId, roundId, jwt){
        try {
          await fetch(`/api/v1/games/${gameId}/rounds/${roundId}/hotPotatoPoints`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
          });
        } catch (error) {
          console.error("Error fetching new card in hot potato:", error);
        }
      }

      async fetchPointsCountHotPotato(gameId, roundId, jwt){
        try {
          await fetch(`/api/v1/games/${gameId}/rounds/${roundId}/pointsPlayerHotPotato`, {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
          });
        } catch (error) {
          console.error("Error fetching new card in hot potato:", error);
        }
      }
}

export default new hotPotato();