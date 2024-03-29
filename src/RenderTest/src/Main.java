public class Main {
    public static void main(String []args) {
        Render r = new Render();
        for(int i=0;i<4;i++) {
            DCalc v = new DCalc();
            v.TellPos(15*i, -300, 8);
            v.Calculate();
        }
    }
}


public class DCalc {
    int[] Xs = new int[100];
    int[] Ys = new int[100];
    int[] Zs = new int[100];


    int[] color = new int[100];
    int x,y,z;
    int pointCount = 3;
    public DCalc() {
        Xs[0] = 600;
        Ys[0] = 400;
        Zs[0] = 2;
        color[0] = 0;
        Xs[1] = 400;
        Ys[1] = 200;
        Zs[1] = 3;
        color[1] = 1;
        Xs[2] = 300;
        Ys[2] = 800;
        Zs[2] = 4;
        color[2] = 2;
        x=0;
        y=0;
        z=0;
        Calculate();
    }
    public void Calculate() {
        for(int i = 0; i<pointCount;i++) {
            int zsto = Zs[i]+z;
            Xs[i] = (Xs[i]+x)/(zsto);
            Ys[i] = (Ys[i]+y)/(zsto);
        }
    }
    public void TellPos(int x,int y,int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int getPoints() {
        return pointCount;
    }
    public int[] getX() {


        return Xs.clone();
    }
    public int[] getY() {




        return Ys.clone();
    }
    public int[] getColor() {
        return color.clone();
    }
}


import java.awt.Color;
        import java.awt.Graphics;
        import javax.swing.*;
public class Screen extends JPanel{
    DCalc DDD = new DCalc();
    int loops = DDD.getPoints();
    public void paintComponent (Graphics g){
        int[] XVals = DDD.getY();
        int[] YVals = DDD.getX();
        for(int i = 0; i<loops*3;i+=3) {
            switch(DDD.getColor()[i]) {
                case 0:
                    g.setColor(Color.BLACK);
                    break;
                case 1:
                    g.setColor(Color.BLUE);
                    break;
                case 2:
                    g.setColor(Color.RED);
                    break;
            }
            int[] x = new int[3];
            x[0] = XVals[i]+300;
            x[1] = XVals[i+1]+300;
            x[2] = XVals[i+2]+300;
            int[] y = new int[3];
            y[0] = YVals[i]+300;
            y[1] = YVals[i+1]+300;
            y[2] = YVals[i+2]+300;
            g.fillPolygon(x,y, 3);
        }


    }


}


import java.awt.Toolkit;
        import javax.swing.JFrame;
public class Render extends JFrame {
    static JFrame F = new Render();
    Screen ScreenObject = new Screen();
    public Render() {
        add(ScreenObject);
        //setUndecorated(true);
        setSize(600,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }




}

