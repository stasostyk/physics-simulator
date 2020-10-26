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
  private int mouseX = 0;
  private int mouseY = 0;

  public void draw(Graphics g)
  {
    g.setColor(Color.BLACK);
    for (int i = 0; i < objects.size(); i++)
      g.fillOval(objects.get(i).x-10, objects.get(i).y-10, 20, 20);

    if (objects.size() <= 0) return;

    if (objects.get(objects.size()-1).isDragging)
    {
      KinematicObject obj = objects.get(objects.size()-1);
      g.setColor(Color.GREEN);
      g.drawString("Vi", 2*obj.x-mouseX+5, 2*obj.y-mouseY);
      g.drawLine(obj.x, obj.y, 2*obj.x-mouseX, 2*obj.y-mouseY);
    }
  }

  public void update(int mouseX, int mouseY)
  {
    this.mouseX = mouseX;
    this.mouseY = mouseY;

    for (int i = 0; i < objects.size(); i++)
    {
      objects.get(i).update();
      objects.get(i).checkCollisions(objects);
    }
  }

  public void mouseClicked(int x, int y)
  {
    objects.get(objects.size()-1).isDragging = false;
  }

  public void mousePressed(int x, int y)
  {
    objects.add(new KinematicObject(x, y));
  }

  public void mouseReleased(int x, int y)
  {
    KinematicObject obj = objects.get(objects.size()-1);
    if (obj.isDragging && (obj.x != x || obj.y != y))
    {
      double xDrag = x - obj.x;
      double yDrag = obj.y - y;
      obj.isDragging = false;
      obj.setInitialVelocity(xDrag, yDrag);
    }
  }

}
