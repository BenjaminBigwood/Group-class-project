import javax.swing.JFrame;
import java.awt.event.*;
public class Display extends JFrame {
    PolyPanel p = new PolyPanel();
    JFrame frame = new JFrame();
    public boolean click = true;
    CustomMouseListener m = new CustomMouseListener();
    Display() {
        p.addMouseListener(m);
        frame.add(p);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void updatePanel() {
        p.updateScene();
    }

    public void click(int v) {
        p.clicked(v);
    }
    public boolean HasBeenClicked() {
        boolean cl = m.retClick();
        return cl;
    }
}

class CustomMouseListener implements MouseListener{

    boolean c = false;
    public void mouseClicked(MouseEvent e) {
        c = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean retClick() {
        return c;
    }

}