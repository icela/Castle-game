package coder;

import java.awt.*;

/**
 * Created by Eldath on 2016/7/24 0024.
 * @author Eldath
 */
public class Delay {
    public void delay(int millisecond)  {
        try {
            Robot r = new Robot();
            r.delay(millisecond);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
