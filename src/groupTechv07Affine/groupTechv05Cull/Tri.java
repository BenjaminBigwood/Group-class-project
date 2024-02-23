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

    int[] tx = new int[3]; //UPDATE THIS LATER
    int[] ty = new int[3]; //DOING LENGTH 3
    int[] tz = new int[3]; //WASTES 12 BYTES
    int color, co, si, co2, si2, tempx, tempx2,tempy,tempz, group;
    boolean culled,face;
    SceneInfo s = Main.s;
    public Tri() {
        int[] sc = s.screenRd();
        sc[0] = scrn[0]/2;
        sc[1] = scrn[1]/2;
    }
    public void pos(int x1, int y1, int z1,int x2, int y2, int z2,int x3, int y3, int z3, int color, boolean face, int group) {
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
        this.group = group;
        Calculate();
    }

    public void Calculate() {
        int[] cam = s.readCam(); //cam[0] is x , 1 is y, 2 is z, 3 is fov.
        si = s.getSin(s.cxr(group)); //its wack but significantly faster than calling (int)math.sin(math.toradian))
        co = s.getSin(s.cxr(group)+64); //this offsets sin by 1/4 wave to get cos, values are -127/127, so 360 degrees = 256 marks and 90 = 64
        si2 = s.getSin(s.cyr(group));
        co2 = s.getSin(s.cyr(group)+64);

        for(int i = 0; i < 3; i++) {
            //tempx = x[i]; tempy = y[i];
            tempx = (x[i]*co)-(y[i]*si);
            tempy = (x[i]*si)+(y[i]*co);
            tempx = tempx >> 7; // faux fixed-p fixer for faster function, finicky
            tempy = tempy >> 7; // multiplied as "24 bit with 8 bit fraction" and converted to just 24 bit number in int

            tempx2 = (tempx*co2)-(z[i]*si2);
            tempz = (tempx*si2)+(z[i]*co2);
            tempx = tempx2 >> 7;
            tempz = tempz >> 7;
            rz[i] = tempz;
            tx[i] = tempx;
            ty[i] = tempy;
            tz[i] = tempz;
        }
            //int[] angle = angleOffset(normal(tx[0],ty[0],tz[0],tx[1],ty[1],tz[1],tx[2],ty[2],tz[2]),1,1,6000); //find the angle of each tri before projection (would be really slow with texturing)
            //System.out.println(angle[0] + " " + angle[1] + " " + angle[2]);
        //if(angle[2] < 350) {
                culled = false;
                for (int i = 0; i < 3; i++) {
                    try {
                        px[i] = 500 + (((tx[i] + cam[0]) * s.fov) / ((tz[i] + cam[2]) >> 2));
                    } catch (ArithmeticException e) {
                        px[i] = 0;
                    }
                    try {
                        py[i] = 500 + (((ty[i] + cam[1]) * s.fov) / ((tz[i] + cam[2]) >> 2));
                    } catch (ArithmeticException e) {
                        px[i] = 0;
                    }
                }
           // } else {px = new int[] {0,0,0}; py = new int[] {0,0,0};}
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

    public boolean culled() {
        return culled;
    }
    public int zAvg() {
        return rz[0]+rz[1]+rz[2];
    }

    int[] normal(int x0, int y0, int z0, int x1, int y1, int z1, int x2, int y2, int z2) {
        int ax = x1-x0; int ay = y1-y0; int az = z1-z0;
        int bx = x2-x0; int by = y2-y0; int bz = z2-z0;
        //System.out.println((ay*bz-az*by) + " " + (az*bx-ax*bz) + " " + (ax*by-ay*bx));

        return new int[] {ay*bz-az*by,az*bx-ax*bz,ax*by-ay*bx};
    }

    int[] dotProd3d(int ax, int ay, int az, int bx, int by, int bz) {
        return new int[] {
          ax*bx,
          ay*by,
          az*bz
        };
    }
    int[] angleOffset(int[] a, int bx, int by, int bz) {
        int[] dot = dotProd3d(a[0], a[1], a[2], bx, by, bz);
        return new int[] {s.getSin(90+(dot[0]/(a[0]+bx))),
                (dot[1]/Math.abs(a[1]+bx)), (dot[2]/Math.abs(a[2]+bx))};
    }
}
