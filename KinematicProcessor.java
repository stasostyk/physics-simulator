import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.Point;

public class KinematicProcessor
{
  private ArrayList<KinematicObject> objects = new ArrayList<KinematicObject>();
  private int mouseX = 0;
  private int mouseY = 0;

  private ArrayList<Point> trail = new ArrayList<Point>(); // ball trail
  private int time_elapsed = 5; // every how much does it plot a point
  private int points = 15; // how many plotted points

  public void draw(Graphics g)
  {
    g.setColor(Color.BLUE);
    for (int i = 0; i < trail.size(); i++)
    {
      g.fillOval(trail.get(i).x, trail.get(i).y, 10,10);
      // System.out.println("drawn");
    }

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

  public void update()
  {
    this.mouseX = Window.x;
    this.mouseY = Window.y;
    // System.out.println(Main.w.getContentPane().getSize());

    if (objects.size()>0)
    {
      int ID = objects.size()-1;

      time_elapsed--;
      // System.out.println(time_elapsed);
      if (time_elapsed == 0)
      {
        if (trail.size() == points)
        {
          trail.remove(0);
          trail.add(new Point(objects.get(ID).x, objects.get(ID).y));
        }
        else
          trail.add(new Point(objects.get(ID).x, objects.get(ID).y));
        time_elapsed = 5;
      }
    }

    for (int i = 0; i < objects.size(); i++)
    {
      objects.get(i).update();
      objects.get(i).checkCollisions(objects);
    }
  }

  public void mouseClicked()
  {
    objects.get(objects.size()-1).isDragging = false;
  }

  public void mousePressed()
  {
    objects.add(new KinematicObject(Window.x, Window.y));
    trail = new ArrayList<Point>();
  }

  public void mouseReleased()
  {
    KinematicObject obj = objects.get(objects.size()-1);
    if (obj.isDragging && (obj.x != Window.x || obj.y != Window.y))
    {
      double xDrag = Window.x - obj.x;
      double yDrag = obj.y - Window.y;
      obj.isDragging = false;
      obj.setInitialVelocity(xDrag, yDrag);
    }
  }

}
