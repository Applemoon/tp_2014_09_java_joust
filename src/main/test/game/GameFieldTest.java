package game;

import org.junit.Before;
import org.junit.Test;
import utils.ReadXMLFileSAX;

import static org.junit.Assert.*;

public class GameFieldTest {
    GameField field;
    int fieldSize;

    @Before
    public void setUp() throws Exception {
        GameSettings serverSettings = (GameSettings) ReadXMLFileSAX.readXML("game_settings.xml");
        field = new GameField(serverSettings.getFieldSize(), serverSettings.getChainToWin());
        fieldSize = serverSettings.getFieldSize();
    }

    @Test
    public void testGetFieldSize() throws Exception {
        assertEquals(fieldSize, field.getFieldSize());
    }

    @Test
    public void testClickCell() throws Exception {
        // за пределами
        ClickResult result = field.clickCell(true, -1, -1);
        assertEquals(ClickResult.NO_RESULT, result);

        result = field.clickCell(true, 0, 0);
        assertEquals(ClickResult.NO_RESULT, result);

        result = field.clickCell(false, field.getFieldSize()-1, field.getFieldSize()-1);
        assertEquals(ClickResult.NO_RESULT, result);

        // нормальный ход
        result = field.clickCell(true, 2, 0);
        assertEquals(ClickResult.FILLED, result);

        // занятая клетка
        result = field.clickCell(false, 2, 0);
        assertEquals(ClickResult.NO_RESULT, result);

        // нормальные ходы
        result = field.clickCell(false, 3, 0);
        assertEquals(ClickResult.FILLED, result);

        result = field.clickCell(true, 2, 1);
        assertEquals(ClickResult.FILLED, result);

        result = field.clickCell(false, 3, 1);
        assertEquals(ClickResult.FILLED, result);

        // победный ход
        result = field.clickCell(true, 2, 2);
        assertEquals(ClickResult.WIN, result);
    }
}