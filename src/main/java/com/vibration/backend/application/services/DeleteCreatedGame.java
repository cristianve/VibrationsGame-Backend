package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.models.DeleteDataOperations;
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
public class DeleteCreatedGame {

    private final UpdateSocketAttributesOperations updateSocketAttributesOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final SearchSessionByGameId searchSessionByGameId;
    private final DeleteDataOperations deleteDataOperations;
    private final SaveSocketSession saveSocketSession;

    public DeleteCreatedGame(UpdateSocketAttributesOperations updateSocketAttributesOperations,
                             GetSocketAttributesOperations getSocketAttributesOperations,
                             SearchSessionByGameId searchSessionByGameId,
                             DeleteDataOperations deleteDataOperations,
                             SaveSocketSession saveSocketSession) {
        this.updateSocketAttributesOperations = updateSocketAttributesOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.searchSessionByGameId = searchSessionByGameId;
        this.deleteDataOperations = deleteDataOperations;
        this.saveSocketSession = saveSocketSession;
    }

    public Map<WebSocketSession, BasicResponseBody> execute(WebSocketSession session) {
        Map<WebSocketSession, BasicResponseBody> responseMap = new ConcurrentHashMap<>();

        var gameId = getSocketAttributesOperations.getGetSocketGameId().execute(session);
        deleteDataOperations.getDeleteGame().execute(gameId);

        List<WebSocketSession> webSocketSessionList = searchSessionByGameId.execute(gameId);

        webSocketSessionList.parallelStream().forEach(userSession -> {
            var userInfo = getSocketAttributesOperations.getGetSocketUser().execute(userSession);

            updateSocketAttributesOperations.getUpdateSocketGameId().execute(userSession, null);
            updateSocketAttributesOperations.getUpdateSocketState().execute(userSession, SocketState.CONNECTED);
            updateSocketAttributesOperations.getUpdateSocketAction().execute(userSession, SocketAction.IDLE);

            var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
            if (user.getUserName().equals(userInfo.getUserName())) {
                updateSocketAttributesOperations.getUpdateSocketUserAdmin().execute(userSession, new UserAdmin(Boolean.FALSE));
            }

            saveSocketSession.execute(userInfo, userSession);

            var responseBody = new BasicResponseBody(0, "GAME DELETED", SocketState.CONNECTED.toString(),
                    SocketAction.EXIT.toString());

            responseMap.put(userSession, responseBody);
        });

        return responseMap;
    }
}
