import java.awt.MouseInfo;
import java.awt.Point;
public class Main {

    public static SceneInfo s = new SceneInfo();
    public static int clicked = 0;

    public static void main(String []args) {
        s.camPos(-2000,-2000,5000);
        s.camRot(0,0,1);
        Display d = new Display();
        int b = -1;
        while (true) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            b++;
            b = (byte) b;
            test(p,d);
            if(clicked == 1) {
                d.click(0);
                s.camPos(0,0,9000);
                s.camRot(b,32,1);
                s.camRot(p.y, p.x,0);
            } else {s.camRot(b,b,0);}
            if(clicked == 2) {
                d.click(1);
                s.camPos(0,0,9000);
                s.camRot(p.y, p.x,0);
            } else {s.camRot(b,b,0);}
            System.out.println((byte)p.x);
            //System.out.println(p.x + " " + p.y);
            //s.camRot();
            //System.out.println(b);
            d.updatePanel();
            try {Thread.sleep(16);} catch(Exception e){ System.out.println("Thread not Eepy ):");}
        }
    }
    public static void test(Point p,Display d) {
        if(p.x >= 500 && p.x <= 700 && p.y <= 200 && p.y >= 0 && d.HasBeenClicked()) {
            clicked = 1;
        }
        if(p.x >= 500 && p.x <= 700 && p.y <= 400 && p.y >= 200 && d.HasBeenClicked()) {
            clicked = 2;
        }
    }
}
