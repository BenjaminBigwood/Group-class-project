import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
public class PolyPanel extends JPanel {
    Tri[] tris = new Tri[14];
    int txs,tys;
    int texture[][] = {
            {150,70,0 ,140,60,0 ,150,70,0 ,170,80,0},
            {150,70,0 ,170,80,0 ,150,70,0 ,140,60,0},
            {0},
            {0}
    };
    int triCount = tris.length;

    public PolyPanel() {
        for (int i = 0; i < triCount; i++) {
            tris[i] = new Tri();
        }
        //front
        tris[0].pos(-2000,-2000,2000,2000,-2000,2000,2000,2000,2000,1,false,0,0,0,4);
        tris[1].pos(-2000,-2000,2000,-2000,2000,2000,2000,2000,2000,1,false,0,0,0,4);
        //back
        tris[2].pos(-2000,-2000,-2000,2000,-2000,-2000,2000,2000,-2000,0,false,0,0,0,4);
        tris[3].pos(-2000,-2000,-2000,-2000,2000,-2000,2000,2000,-2000,0,false,0,0,0,4);
        //left
        tris[4].pos(2000,2000,2000,2000,-2000,2000,2000,2000,-2000,2,false,0,0,0,4);
        tris[5].pos(2000,-2000,2000,2000,-2000,-2000,2000,2000,-2000,2,false,0,0,0,4);
        //right
        tris[6].pos(-2000,2000,2000,-2000,-2000,2000,-2000,2000,-2000,3,false,0,0,0,4);
        tris[7].pos(-2000,-2000,2000,-2000,-2000,-2000,-2000,2000,-2000,3,false,0,0,0,4);
        //top
        tris[8].pos(-2000,2000,2000,-2000,2000,-2000,2000,2000,2000,4,false,0,0,0,4);
        tris[9].pos(-2000,2000,-2000,2000,2000,-2000,2000,2000,2000,4,false,0,0,0,4);
        //bottom
        tris[10].pos(-2000,-2000,2000,-2000,-2000,-2000,2000,-2000,2000,5,false,0,0,0,4);
        tris[11].pos(-2000,-2000,-2000,2000,-2000,-2000,2000,-2000,2000,5,false,0,0,1,4);
        tris[12].pos(3000,3000,-2000,5000,3000,-2000,3000,5000,-2000,5,false,1,0,0,4);
        tris[13].pos(3000,5000,-2000,5000,3000,-2000,5000,5000,-2000,5,false,1,0,1,4);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black); //to make it work on pc
        g.fillRect(0,0,1000,1000); //same with this
        for (int i = 0; i < triCount; i++) {
            tris[i].Calculate();
        }
        int[] order = sortZ(tris);
        for (int i = 0; i < triCount; i++) {
            if (!tris[i].culled()) {
                int iord = order[i];
                int[] tx = tris[iord].x();
                int[] ty = tris[iord].y();
                int res = tris[iord].res();
                int fill = 50;
                int[] sx = new int[(res*2)+(res-1)*2];
                int[] sy = new int[(res*2)+(res-1)*2];
                int[] ax = new int[fill];
                int[] ay = new int[fill];
                int turns = tris[iord].turns();
                    int temp;
                    for(int j = 0; j<turns; j++) {
                        temp = sx[0];
                        sx[0] = sx[1];
                        sx[1] = sx[2];
                        sx[2] = temp;
                        temp = sy[0];
                        sy[0] = sy[1];
                        sy[1] = sy[2];
                        sy[2] = temp;
                    }
                g.setColor(Color.lightGray);
                for(int j = 0; j<res+1;j++) { //triangle sides
                    splitLine(tx[1], tx[0], ty[1], ty[0], j, res);
                    sx[j] = txs;
                    sy[j] = tys;
                    splitLine(tx[1], tx[2], ty[1], ty[2], j, res);              //WORKING
                    sx[j + res + 1] = txs;
                    sy[j + res + 1] = tys;
                    //System.out.println("a " + j);
                }
                g.setColor(Color.blue);
                int yMul = 0;
                int width = res+1;
                for(int j = 0; j<res;j++) { //triangle body + sides included
                    for(int k = 0; k<width; k++){
                        splitLine(sx[j],sx[j+res+1],sy[j],sy[j+res+1],k,width-1);
                        ax[k+yMul] = txs; ay[k+yMul] = tys;
                        //System.out.println(txs + " " + tys + " " + (k+yMul));
                        g.drawOval(txs,tys,5, 5);
                    }
                    ax[(res*2)+(res-1)*2] = sx[res];
                    ay[(res*2)+(res-1)*2] = sy[res];
                    g.drawOval(ax[14],ay[14],5, 5);
                    yMul = yMul+width;
                    width = width-1;
                }
                g.setColor(Color.darkGray);
                g.drawPolygon(new int[] {tx[0],tx[1],tx[2]},new int[] {ty[0],ty[1],ty[2]},3);
                yMul = 0;
                width = res;
                for(int j = 1; j<res-1; j++) {
                    for (int k = 1; k<width; k++) {
                        g.fillPolygon(new int[]{ax[k+((res+1)*(j-1))], ax[k+1+((res+1)*(j-1))], ax[k+((res)*j)+1], ax[k+((res)*j)]}, new int[]{ay[k+((res+1)*(j-1))], ay[k+1+((res+1)*(j-1))], ay[k+((res)*j)+1], ay[k+((res)*j)]}, 4);
                    }
                    yMul = yMul+width;
                    width--;
                    g.setColor(Color.lightGray);
                }
            }
        }
        //g.drawOval(5, 5, 5, 5);
    }

    public void updateScene() {
        revalidate();
        repaint();
    }

    void splitLine(int x0, int x1, int y0, int y1, int division, int res) {
        txs = ((x0<<8) * ((division<<8) / res)>>8) + ((x1<<8) * (((res-division)<<8) / res)>>8)>>8;
        tys = ((y0<<8) * ((division<<8) / res)>>8) + ((y1<<8) * (((res-division)<<8) / res)>>8)>>8;
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

// g.fillPolygon(new int[]{ax[7], ax[6], ax[9], ax[10]}, new int[]{ay[7], ay[6], ay[9], ay[10]}, 4);