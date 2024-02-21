import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
public class PolyPanel extends JPanel {
    Tri[] tris = new Tri[12];
    int triCount = tris.length;

    public PolyPanel() {
        for (int i = 0; i < triCount; i++) {
            tris[i] = new Tri();
        }
        //front
        tris[0].pos(-2000,-2000,2000,2000,-2000,2000,2000,2000,2000,1,false);
        tris[1].pos(-2000,-2000,2000,-2000,2000,2000,2000,2000,2000,1,false);
        //back
        tris[2].pos(-2000,-2000,-2000,2000,-2000,-2000,2000,2000,-2000,0,false);
        tris[3].pos(-2000,-2000,-2000,-2000,2000,-2000,2000,2000,-2000,0,false);
        //left
        tris[4].pos(2000,2000,2000,2000,-2000,2000,2000,2000,-2000,2,false);
        tris[5].pos(2000,-2000,2000,2000,-2000,-2000,2000,2000,-2000,2,false);
        //right
        tris[6].pos(-2000,2000,2000,-2000,-2000,2000,-2000,2000,-2000,3,false);
        tris[7].pos(-2000,-2000,2000,-2000,-2000,-2000,-2000,2000,-2000,3,false);
        //top
        tris[8].pos(-2000,2000,2000,-2000,2000,-2000,2000,2000,2000,4,false);
        tris[9].pos(-2000,2000,-2000,2000,2000,-2000,2000,2000,2000,4,false);
        //bottom
        tris[10].pos(-2000,-2000,2000,-2000,-2000,-2000,2000,-2000,2000,5,false);
        tris[11].pos(-2000,-2000,-2000,2000,-2000,-2000,2000,-2000,2000,5,false);
    }

    public void paintComponent(Graphics g) {
        //g.setColor(Color.white); //to make it work on pc
        //g.fillRect(0,0,1000,1000); //same with this
        for (int i = 0; i < triCount; i++) {
            tris[i].Calculate();
        }
        int[] order = sortZ(tris);
        for (int i = 0; i < triCount; i++) {
            switch (tris[order[i]].c()) {
                case 0:
                    g.setColor(Color.black);
                    break;
                case 1:
                    g.setColor(Color.blue);
                    break;
                case 2:
                    g.setColor(Color.green);
                    break;
                case 3:
                    g.setColor(Color.red);
                    break;
                case 4:
                    g.setColor(Color.orange);
                    break;
                case 5:
                    g.setColor(Color.magenta);
                    break;
            }
            g.fillPolygon(tris[order[i]].x(),tris[order[i]].y(),3);
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
