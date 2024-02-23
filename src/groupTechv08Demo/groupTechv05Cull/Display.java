import javax.swing.JFrame;
public class Display extends JFrame {
    PolyPanel p = new PolyPanel();
    Display() {
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void updatePanel() {
        p.updateScene();
    }
}
