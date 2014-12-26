package interfaces.services;

import frontend.GameWebSocket;

public interface WebSocketService {

    void addUserSocket(GameWebSocket user);

    void notifyGameOver(String user, String winner);

    void notifyCellFilled(String user, int x, int y, String nameFilled);

    void notifyStandOff(String user);

    void removeSocket(GameWebSocket userSocket);
}
