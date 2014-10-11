package utils;

/**
 * Created by alexey on 25.09.14.
 */
public class TimeHelper {
    public static void sleep(int period) {
        try {
            Thread.sleep(period);
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
