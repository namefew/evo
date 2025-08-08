export const handleSocketMessage = (socketMessage, state) => {
  const {
    MessageType,
    subMessageType,
    TableId,
    tableId,
    balance: _balance,
    data,
    ...rest
  } = socketMessage;

  const id = tableId || TableId;

  switch (MessageType) {
  // Update `playersCount` for each table
  case INITIAL_DATA: {
    return handleInitialData(socketMessage, state);
  }
  // Update `balance`
  case CURRENT_BALANCE: {
    return handleCurrentBalance(socketMessage);
  }
  // Update `playersCount` for one table
  case PLAYERS_NUMBER: {
    return handlePlayersNumber(socketMessage, state);
  }

  case PLAYER_LIMITS_NOTIFICATION: {
    return of(...handlePlayerNotifications(data));
  }
  case UPDATED_LOBBY_DATA: {
    switch (subMessageType) {
    case INITIALIZE_TABLE: {
      return of(
        tables.updateTable([
          {
            tableId: id,
            active: true,
          },
        ])
      );
    }
    case UPDATE_BLALANCE: // TODO: remove this once the typo is fixed on the GameEngine
    case UPDATE_BALANCE: {
      return handleCurrentBalance(socketMessage);
    }
    case CANCEL_TABLE: {
      return of(
        tables.updateTable([
          {
            tableId: id,
            active: false,
            history: [],
          },
        ])
      );
    }
    case UPDATED_TABLE_HISTORY:
    case UPDATE_FREE_SEATS: {
      return handleTableHistory(socketMessage, state);
    }
    case ROUND_START: {
      return of(
        tables.updateTable([
          {
            tableId: id,
            round_start_time: {
              time_left: rest.TimerTimeLeft ? rest.TimerTimeLeft : 0,
              timestamp: Date.now(),
            },
          },
        ])
      );
    }
    case CHANGE_DEALER: {
      return of(
        tables.updateTable([
          {
            tableId: id,
            dealer_name: rest.DealerName,
          },
        ])
      );
    }
    case UPDATE_DEALER_IMAGE: {
      return handleDealerImage(socketMessage, state);
    }
    case SHUFFLE: {
      return of(
        tables.updateTable([
          {
            tableId: id,
            history: [],
          },
        ])
      );
    }
    case TABLE_OCCUPANCY: {
      return handleTableOccupancy(socketMessage, state);
    }
    default: {
      return EMPTY;
    }
    }
  }
  default: {
    return EMPTY;
  }
  }
};

function handleTableHistory(socketMessage, state) {
  const { tableId, TableId, History, GameResults, AvailableSeats } = socketMessage;
  const id = tableId || TableId;

  let table = tablesSelector(state)[id];

  if (!table) {
    table = { tableId: id, history: [] };
  }

  const { history, playersCount, gameId } = table;

  let newPlayerCount = playersCount;

  const nextHistory = produce(history, (draftState) => {
    if (typeof History !== 'undefined') {
      draftState = History.slice(
        -getMaxHistoryByGame(table.gameId)
      );
    }
    if (typeof GameResults !== 'undefined') {
      draftState.push(GameResults);
    }
    if (typeof AvailableSeats !== 'undefined') {
      draftState = AvailableSeats;
    }

    if (isBlackjack(gameId)) {
      newPlayerCount = draftState.filter(
        (seat) => seat.SeatId !== 'd' && seat.Taken
      ).length;
    }

    return draftState;
  });

  return of(
    tables.updateTable([
      {
        tableId: id,
        history: nextHistory,
        playersCount: newPlayerCount,
      },
    ])
  );
}