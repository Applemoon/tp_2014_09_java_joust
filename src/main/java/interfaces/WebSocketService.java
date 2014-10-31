package interfaces;

import frontend.GameWebSocket;

public interface WebSocketService {

    void addUserSocket(GameWebSocket user);

    void notifyGameOver(String user, boolean win);

    void removeSocket(GameWebSocket userSocket);
}
