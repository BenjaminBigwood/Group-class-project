import java.util.Collections;
public class Tri{
    int[] x = new int[3];
    int[] y = new int[3];
    int[] z = new int[3];

    int[] rz = new int[3];
    int[] px = new int[3];
    int[] py = new int[3];
    int[] scrn = new int[2];
    int[] sc = new int[2];
    int color, co, si, co2, si2, tempx, tempx2,tempy,tempz;
    boolean face;
    SceneInfo s = Main.s;
    public Tri() {
        int[] sc = s.screenRd();
        sc[0] = scrn[0]/2;
        sc[1] = scrn[1]/2;
    }
    public void pos(int x1, int y1, int z1,int x2, int y2, int z2,int x3, int y3, int z3, int color, boolean face) {
        this.x[0] = x1;
        this.y[0] = y1;
        this.z[0] = z1;
        this.x[1] = x2;
        this.y[1] = y2;
        this.z[1] = z2;
        this.x[2] = x3;
        this.y[2] = y3;
        this.z[2] = z3;
        this.face = face;
        this.color = color;
        Calculate();
    }

    public void Calculate() {
        int[] cam = s.readCam(); //cam[0] is x , 1 is y, 2 is z, 3 is fov.
        si = s.getSin(s.cxr()); //its wack but significantly faster than calling (int)math.sin(math.toradian))
        co = s.getSin(s.cxr()+64); //this offsets sin by 1/4 wave to get cos, values are -127/127, so 360 degrees = 256 marks and 90 = 64
        si2 = s.getSin(s.cyr());
        co2 = s.getSin(s.cyr()+64);

        for(int i = 0; i < 3; i++) {
            //tempx = x[i]; tempy = y[i];
            tempx = (x[i]*co)-(y[i]*si);
            tempy = (x[i]*si)+(y[i]*co);
            tempx = tempx >> 8; // faux fixed-p fixer for faster function, finicky
            tempy = tempy >> 8; // multiplied as "24 bit with 8 bit fraction" and converted to just 24 bit number in int
            tempx2 = (tempx*co2)-(z[i]*si2);
            tempz = (tempx*si2)+(z[i]*co2);
            tempx = tempx2 >> 8;
            tempz = tempz >> 8;
            rz[i] = tempz;
            try {
                px[i] = 500 + (((tempx + cam[0]) * s.fov) / ((tempz + cam[2]) >> 3));
            } catch(ArithmeticException e) {
                px[i] = 0;
            }
            try {
                py[i] = 500 + (((tempy + cam[1]) * s.fov) / ((tempz + cam[2]) >> 3));
            } catch (ArithmeticException e) {
                px[i] = 0;
            }
        }
    }
    public int[] x() {
        //System.out.println(px[1]);
        return px.clone();
    }
    public int[] y() {
        //System.out.println(py[1]);
        return py.clone();
    }
    public int c() {
        return color;
    }

    public boolean dir() {
        return face;
    }
    public int zAvg() {
        return rz[0]+rz[1]+rz[2];
    }

    int[] normal(int x0, int y0, int z0, int x1, int y1, int z1, int x2, int y2, int z2) {
        int ax = x1-x0; int ay = y1-y0; int az = z1-z0;
        int bx = x2-x0; int by = y2-y0; int bz = z2-z0;

        return new int[] {ay*bz-az*by,az*bx-ax*bz,ax*by-ay*bx};
    }
    int angleOffset(int dp, int x, int y, int z) {
        return dp/(Math.abs(x) * Math.abs(y) * Math.abs(z));
    }
}
