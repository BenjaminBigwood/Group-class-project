import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
public class PolyPanel extends JPanel {
    Tri[] tris = new Tri[14];
    int triCount = tris.length;

    public PolyPanel() {
        for (int i = 0; i < triCount; i++) {
            tris[i] = new Tri();
        }
        //front
        tris[0].pos(-2000,-2000,2000,2000,-2000,2000,2000,2000,2000,1,false,0);
        tris[1].pos(-2000,-2000,2000,-2000,2000,2000,2000,2000,2000,1,false,0);
        //back
        tris[2].pos(-2000,-2000,-2000,2000,-2000,-2000,2000,2000,-2000,0,false,0);
        tris[3].pos(-2000,-2000,-2000,-2000,2000,-2000,2000,2000,-2000,0,false,0);
        //left
        tris[4].pos(2000,2000,2000,2000,-2000,2000,2000,2000,-2000,2,false,0);
        tris[5].pos(2000,-2000,2000,2000,-2000,-2000,2000,2000,-2000,2,false,0);
        //right
        tris[6].pos(-2000,2000,2000,-2000,-2000,2000,-2000,2000,-2000,3,false,0);
        tris[7].pos(-2000,-2000,2000,-2000,-2000,-2000,-2000,2000,-2000,3,false,0);
        //top
        tris[8].pos(-2000,2000,2000,-2000,2000,-2000,2000,2000,2000,4,false,0);
        tris[9].pos(-2000,2000,-2000,2000,2000,-2000,2000,2000,2000,4,false,0);
        //bottom
        tris[10].pos(-2000,-2000,2000,-2000,-2000,-2000,2000,-2000,2000,5,false,0);
        tris[11].pos(-2000,-2000,-2000,2000,-2000,-2000,2000,-2000,2000,5,false,0);
        tris[12].pos(3000,3000,-2000,5000,3000,-2000,3000,5000,-2000,5,false,1);
        tris[13].pos(3000,5000,-2000,5000,3000,-2000,5000,5000,-2000,5,false,1);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.white); //to make it work on pc
        g.fillRect(0,0,1000,1000); //same with this
        for (int i = 0; i < triCount; i++) {
            tris[i].Calculate();
        }
        int[] order = sortZ(tris);
        for (int i = 0; i < triCount; i++) {
            if (!tris[i].culled()) {
//                switch (tris[order[i]].c()) {
//                    case 0:
//                        g.setColor(Color.black);
//                        break;
//                    case 1:
//                        g.setColor(Color.blue);
//                        break;
//                    case 2:
//                        g.setColor(Color.green);
//                        break;
//                    case 3:
//                        g.setColor(Color.red);
//                        break;
//                    case 4:
//                        g.setColor(Color.orange);
//                        break;
//                    case 5:
//                        g.setColor(Color.magenta);
//                        break;
//                }
                int[] tx = tris[order[i]].x();
                int[] ty = tris[order[i]].y();
                int[] mx = new int[3];
                int[] my = new int[3];
                mx[0] = (tx[0]+tx[1])/2;
                mx[1] = (tx[1]+tx[2])/2;
                mx[2] = (tx[0]+tx[2])/2;

                my[0] = (ty[0]+ty[1])/2;
                my[1] = (ty[1]+ty[2])/2;
                my[2] = (ty[0]+ty[2])/2;
                //g.setColor(Color.black);
                //g.drawPolygon(tris[order[i]].x(), tris[order[i]].y(), 3);
                g.setColor(new Color (100,50,0));
                g.fillPolygon(new int[] {tx[1],mx[0],mx[2]},new int[] {ty[1],my[0],my[2]}, 3);
                g.setColor(new Color (120,60,40));
                g.fillPolygon(new int[] {tx[0],mx[0],mx[2]},new int[] {ty[0],my[0],my[2]}, 3);
                g.setColor(new Color (150,70,0));
                g.fillPolygon(new int[] {mx[2],mx[1],tx[1]},new int[] {my[2],my[1],ty[1]}, 3);
                g.setColor(new Color (170,80,0));
                g.fillPolygon(new int[] {mx[2],mx[1],tx[2]},new int[] {my[2],my[1],ty[2]}, 3);
            }
        }
        g.drawOval(5, 5, 5, 5);
    }

    public void updateScene() {
        revalidate();
        repaint();
    }

    int[] sortZ(Tri[] t) {
        int[] zArr = new int[t.length];
        for (int i = 0; i < t.length; i++) {
            zArr[i] = t[i].zAvg();
        }
        List<Element> elements = new ArrayList<Element>();
        for (int i = 0; i < zArr.length; i++) {
            elements.add(new Element(i, zArr[i]));
        }
        Collections.sort(elements);
        Collections.reverse(elements);
        for(int i = 0; i<zArr.length;i++) {
            zArr[i] = elements.get(i).index;
        }
        return zArr;
    }
}
