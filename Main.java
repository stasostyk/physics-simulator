import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main
{
  public static JFrame w;

  public static void main(String[] args)
  {
    w = new JFrame("Physics Simulator");
    w.setSize(500, 500);

    Container c = w.getContentPane();
    Window screen = new Window();
    c.add(screen);
    c.setPreferredSize(new Dimension(500,500));
    w.pack();

    w.addKeyListener(screen);
    w.addMouseListener(screen);

    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // w.setResizable(false);
    w.setVisible(true);
    w.setLocationRelativeTo(null);
    w.setFocusable(true);
  }
}
