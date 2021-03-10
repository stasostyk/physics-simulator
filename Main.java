import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.UIManager;
import Engine.Window;

public class Main
{
  public static JFrame w;

  public static void main(String[] args)
  {
    w = new JFrame("Physics Simulator");
    w.setSize(500, 500);

    Container c = w.getContentPane();
    Window screen = new Window(w);
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

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
