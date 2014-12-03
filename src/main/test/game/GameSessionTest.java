package game;

import utils.ReadXMLFileSAX;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameSessionTest {
    private String username1;
    private String username2;
    private GameSession gameSession;

    private GameSession getGameSession() {
        return new GameSession(username1, username2);
    }

    @Before
    public void setUp() throws Exception {
        username1 = "user1";
        username2 = "user2";
        gameSession = getGameSession();
    }

    private void simulateGame() {
        // за пределами
        ClickResult result = gameSession.clickCell(username1, -1, -1);
        assertEquals(ClickResult.NO_RESULT, result);

        result = gameSession.clickCell(username1, 0, 0);
        assertEquals(ClickResult.NO_RESULT, result);

        result = gameSession.clickCell(username2,
                gameSession.getFieldSize()-1, gameSession.getFieldSize()-1);
        assertEquals(ClickResult.NO_RESULT, result);

        // нормальный ход
        result = gameSession.clickCell(username1, 2, 0);
        assertEquals(ClickResult.FILLED, result);

        // занятая клетка
        result = gameSession.clickCell(username2, 2, 0);
        assertEquals(ClickResult.NO_RESULT, result);

        // нормальный ход
        result = gameSession.clickCell(username2, 3, 0);
        assertEquals(ClickResult.FILLED, result);

        // не в свою очередь
        result = gameSession.clickCell(username2, 2, 1);
        assertEquals(ClickResult.NO_RESULT, result);

        // нормальные ходы
        result = gameSession.clickCell(username1, 2, 1);
        assertEquals(ClickResult.FILLED, result);

        result = gameSession.clickCell(username2, 3, 1);
        assertEquals(ClickResult.FILLED, result);

        // победный ход
        result = gameSession.clickCell(username1, 2, 2);
        assertEquals(ClickResult.WIN, result);
    }

    @Test
    public void testClickCell() throws Exception {
        simulateGame();
    }

    @Test
    public void testGetEnemyName() throws Exception {
        assertEquals(username1, gameSession.getEnemyName(username2));
        assertEquals(username2, gameSession.getEnemyName(username1));
        assertEquals("", gameSession.getEnemyName("wrongName"));
    }

    @Test
    public void testIsGameOver() throws Exception {
        gameSession = getGameSession();
        assertFalse(gameSession.isGameOver());
        simulateGame();
        assertTrue(gameSession.isGameOver());
    }

    @Test
    public void testGetFieldSize() throws Exception {
        GameSettings serverSettings = (GameSettings) ReadXMLFileSAX.readXML("game_settings.xml");
        assertEquals(serverSettings.getFieldSize(), gameSession.getFieldSize());
    }
}