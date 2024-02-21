import java.awt.MouseInfo;
import java.awt.Point;
public class Main {

    public static SceneInfo s = new SceneInfo();

    public static void main(String []args) {
        s.camPos(0,0,1);
        Display d = new Display();
        int b = -1;
        while (true) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            b++;
            b = (byte) b;
            //s.camPos((sinTable[((byte) b) +128]),(sinTable[((byte) (b + 90)) +128]),1);
            s.camRot(p.y,p.x);
            s.camPos(0,0,2000);
            //s.camRot();
            //System.out.println(b);
            d.updatePanel();
            try {Thread.sleep(16);} catch(Exception e){ System.out.println("Thread not Eepy ):");}
        }
    }
}
