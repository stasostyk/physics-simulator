import java.awt.Container;
import javax.swing.JFrame;

public class Main
{
  public static void main(String[] args)
  {
    JFrame w = new JFrame("Physics Simulator");
    w.setSize(500, 500);

    Container c = w.getContentPane();
    c.add(new Window());

    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    w.setResizable(false);
    w.setVisible(true);
    w.setLocationRelativeTo(null);
  }
}
