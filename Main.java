import java.awt.Container;
import javax.swing.JFrame;

public class Main
{
  public static JFrame w;
  public static void main(String[] args)
  {
    w = new JFrame("Physics Simulator");
    w.setSize(500, 500);

    Container c = w.getContentPane();
    Window win = new Window();
    c.add(win);

    w.addKeyListener(win);
    w.addMouseListener(win);

    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    w.setResizable(false);
    w.setVisible(true);
    w.setLocationRelativeTo(null);
    w.setFocusable(true);
  }
}
