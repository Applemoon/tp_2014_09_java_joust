package interfaces;

import frontend.GameWebSocket;

public interface WebSocketService {

    void addUserSocket(GameWebSocket user);

    void notifyGameOver(String user, String winner);

    void removeSocket(GameWebSocket userSocket);
}
