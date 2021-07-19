package com.vibration.backend.application.models;

import com.vibration.backend.domain.services.coordinates.DeleteCoordinates;
import com.vibration.backend.domain.services.game.DeleteGame;
import com.vibration.backend.domain.services.playuserdata.DeletePlayUserData;
import com.vibration.backend.domain.services.ranking.DeleteRanking;
import com.vibration.backend.domain.services.session.DeleteSocketSession;
import org.springframework.stereotype.Component;

@Component
public class DeleteDataOperations {

    private final DeleteRanking deleteRanking;
    private final DeleteGame deleteGame;
    private final DeleteCoordinates deleteCoordinates;
    private final DeletePlayUserData deletePlayUserData;
    private final DeleteSocketSession deleteSocketSession;

    public DeleteDataOperations(DeleteRanking deleteRanking,
                                DeleteGame deleteGame,
                                DeleteCoordinates deleteCoordinates,
                                DeletePlayUserData deletePlayUserData,
                                DeleteSocketSession deleteSocketSession) {
        this.deleteRanking = deleteRanking;
        this.deleteGame = deleteGame;
        this.deleteCoordinates = deleteCoordinates;
        this.deletePlayUserData = deletePlayUserData;
        this.deleteSocketSession = deleteSocketSession;
    }

    public DeleteRanking getDeleteRanking() {
        return deleteRanking;
    }

    public DeleteGame getDeleteGame() {
        return deleteGame;
    }

    public DeleteCoordinates getDeleteCoordinates() {
        return deleteCoordinates;
    }

    public DeletePlayUserData getDeletePlayUserData() {
        return deletePlayUserData;
    }

    public DeleteSocketSession getDeleteSocketSession() {
        return deleteSocketSession;
    }
}
