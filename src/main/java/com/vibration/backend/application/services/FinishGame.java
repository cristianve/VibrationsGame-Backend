package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import com.vibration.backend.domain.services.session.SearchSessionByGameId;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FinishGame {

    private final SearchSessionByGameId searchSessionByGameId;
    private final UpdateSocketAttributesOperations updateSocketAttributesOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final SaveSocketSession saveSocketSession;

    public FinishGame(SearchSessionByGameId searchSessionByGameId, UpdateSocketAttributesOperations updateSocketAttributesOperations,
                      GetSocketAttributesOperations getSocketAttributesOperations,
                      SaveSocketSession saveSocketSession) {
        this.searchSessionByGameId = searchSessionByGameId;
        this.updateSocketAttributesOperations = updateSocketAttributesOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.saveSocketSession = saveSocketSession;
    }

    public Map<WebSocketSession, BasicResponseBody> execute(WebSocketSession session) {
        Map<WebSocketSession, BasicResponseBody> responseBodies = new ConcurrentHashMap<>();

        //Get all sessions
        var gameId = getSocketAttributesOperations.getGetSocketGameId().execute(session);
        List<WebSocketSession> webSocketSessions = searchSessionByGameId.execute(gameId);

        //Update all to end
        webSocketSessions.parallelStream().forEach(userSession -> {
            updateSocketAttributesOperations.getUpdateSocketState().execute(userSession, SocketState.END);
            updateSocketAttributesOperations.getUpdateSocketAction().execute(userSession, SocketAction.IDLE);

            var user = getSocketAttributesOperations.getGetSocketUser().execute(userSession);
            saveSocketSession.execute(user, userSession);

            var responseBody = new BasicResponseBody(0, "GAME ENDED", SocketState.END.toString(),
                    SocketAction.IDLE.toString());
            responseBodies.put(userSession, responseBody);
        });

        return responseBodies;
    }
}
