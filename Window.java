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
  private ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();

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
    g.setColor(Color.BLACK);

    super.paintComponent(g);

    for (int i = 0; i < objects.size(); i++)
      g.fillOval(objects.get(i).x-10, objects.get(i).y-10, 20, 20);

  }

  public void actionPerformed(ActionEvent e)
  {
    time++;
    for (int i = 0; i < objects.size(); i++)
    {
      objects.get(i).update();
      objects.get(i).checkCollisions(objects);
    }

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
    objects.add(new PhysicsObject(e.getX(), e.getY()));
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    PhysicsObject obj = objects.get(objects.size()-1);
    if (obj.isDragging && (obj.x != e.getX() || obj.y != e.getY()))
    {
      double xDrag = e.getX() - obj.x;
      double yDrag = obj.y - e.getY();
      obj.setInitialVelocity(xDrag, yDrag);
    }
    obj.isDragging = false;
  }

}
