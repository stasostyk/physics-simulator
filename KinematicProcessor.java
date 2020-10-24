import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;

public class KinematicProcessor
{
  private ArrayList<KinematicObject> objects = new ArrayList<KinematicObject>();

  public void draw(Graphics g)
  {
    g.setColor(Color.BLACK);
    for (int i = 0; i < objects.size(); i++)
      g.fillOval(objects.get(i).x-10, objects.get(i).y-10, 20, 20);
  }

  public void update()
  {
    for (int i = 0; i < objects.size(); i++)
    {
      objects.get(i).update();
      objects.get(i).checkCollisions(objects);
    }
  }

  public void mousePressed(MouseEvent e)
  {
    objects.add(new KinematicObject(e.getX(), e.getY()));
  }

  public void mouseReleased(MouseEvent e)
  {
    KinematicObject obj = objects.get(objects.size()-1);
    if (obj.isDragging && (obj.x != e.getX() || obj.y != e.getY()))
    {
      double xDrag = e.getX() - obj.x;
      double yDrag = obj.y - e.getY();
      obj.setInitialVelocity(xDrag, yDrag);
    }
    obj.isDragging = false;
  }

}
