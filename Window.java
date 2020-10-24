import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;

public class Window extends JPanel
                  implements ActionListener, MouseListener
{
  private int time;
  private KinematicProcessor kine = new KinematicProcessor();


  public Window()
  {
    time = 0;
    Timer clock = new Timer(30, this);
    clock.start();
    addMouseListener(this);
  }

  public void paintComponent(Graphics g)
  {
    setBackground(Color.WHITE);
    super.paintComponent(g);

    kine.draw(g);
  }

  public void actionPerformed(ActionEvent e)
  {
    time++;
    kine.update();

    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
  }

  @Override
  public void mouseEntered(MouseEvent e)
  {}

  @Override
  public void mouseExited(MouseEvent e)
  {}

  @Override
  public void mousePressed(MouseEvent e)
  {
    kine.mousePressed(e);
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    kine.mouseReleased(e);
  }

}
