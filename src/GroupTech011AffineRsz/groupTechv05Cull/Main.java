import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.*;
public class Main {

    public static SceneInfo s = new SceneInfo();
    public static boolean clicked;

    public static void main(String []args) {
        s.camPos(-2000,-2000,5000);
        s.camRot(0,0,1);
        Display d = new Display();
        int b = -1;
        while (true) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            b++;
            b = (byte) b;
            if(p.x >= 500 && p.x <= 700 && p.y <= 200 && p.y >= 0) {
                clicked = true;
            }
            if(clicked) {
                s.camPos(0,0,9000);
                s.camRot(b,85,1);
                s.camRot(p.y, p.x,0);
            } else {s.camRot(b,b,0);}
            d.updatePanel();
            try {Thread.sleep(16);} catch(Exception e){ System.out.println("Thread not Eepy ):");}
        }
    }
}
