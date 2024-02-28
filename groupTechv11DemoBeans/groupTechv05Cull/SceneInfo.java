public class SceneInfo {
    //contains all the global data for the scene
    public int cx,cy,cz,fov,screenX,screenY;
    public byte[] xrot = new byte[3];
    public byte[] yrot = new byte[3];

    final byte[] sinTable = {0,3,6,9,12,15,18,21,24,28,31,34,37,40,43,46,48,51,54,57,60,63,65,68,71,73,76,78,81,83,85,88,90,92,94,96,98,100,102,104,106,108,109,111,112,114,115,117,118,119,120,121,122,123,124,124,125,126,126,127,127,127,127,127,127,127,127,127,127,127,126,126,125,124,124,123,122,121,120,119,118,117,115,114,112,111,109,108,106,104,102,100,98,96,94,92,90,88,85,83,81,78,76,73,71,68,65,63,60,57,54,51,48,46,43,40,37,34,31,28,24,21,18,15,12,9,6,3,0,-3,-6,-9,-12,-15,-18,-21,-24,-28,-31,-34,-37,-40,-43,-46,-48,-51,-54,-57,-60,-63,-65,-68,-71,-73,-76,-78,-81,-83,-85,-88,-90,-92,-94,-96,-98,-100,-102,-104,-106,-108,-109,-111,-112,-114,-115,-117,-118,-119,-120,-121,-122,-123,-124,-124,-125,-126,-126,-127,-127,-127,-127,-127,-128,-127,-127,-127,-127,-127,-126,-126,-125,-124,-124,-123,-122,-121,-120,-119,-118,-117,-115,-114,-112,-111,-109,-108,-106,-104,-102,-100,-98,-96,-94,-92,-90,-88,-85,-83,-81,-78,-76,-73,-71,-68,-65,-63,-60,-57,-54,-51,-48,-46,-43,-40,-37,-34,-31,-28,-24,-21,-18,-15,-12,-9,-6,-3};
    public SceneInfo() {
        fov = (int) Math.pow(2,7); //bitshift left count
        screenX = 1000;
        screenY = 1000;
    }


    public void camPos(int x,int y,int z) {
        this.cx = x;
        this.cy = y;
        this.cz = z;
    }

    public void camRot(int xr, int yr,int group) {
        xrot[group] =(byte) xr;
        yrot[group] =(byte) yr;
    }

    public int cxr(int group) {
        return xrot[group];
    }

    public int cyr(int group) {return yrot[group];}
    public int[] readCam() {
        return new int[] {cx,cy,cz,fov};
    }
    public int[] screenRd() {
        return new int[] {screenX,screenY};
    }

    public int getSin(int s) {
        return sinTable[128 + ((byte) s)];
    }
}
