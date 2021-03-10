package Engine;

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

  // variables for trail effects
  private ArrayList<Point> trail = new ArrayList<Point>(); // ball trail
  private int time_elapsed = 5; // every how much does it plot a point
  private int points = 15; // how many plotted points

  // variables for the prediction trail
  private ArrayList<Point> prediction = new ArrayList<Point>();
  // time_elapsed and points will be shared with trail


  public void draw(Graphics g)
  {
    g.setColor(Color.RED);
    for (int i = 0; i < prediction.size(); i++)
    {
      if (objects.get(objects.size()-1).isDragging)
        predict((Window.x - objects.get(objects.size()-1).x) / -10, (Window.y - objects.get(objects.size()-1).y)/ -10);
      int newX = prediction.get(i).x;
      int newY = prediction.get(i).y;
      g.fillOval(newX, newY, 10, 10); // has to be drawn relative to original position
    }

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
      g.setColor(Color.RED);
      g.drawString("Vit+0.5at^2", 2*obj.x-mouseX+25, 2*obj.y-mouseY-25);
    }
  }

  public void update()
  {
    this.mouseX = Window.x;
    this.mouseY = Window.y;
    // System.out.println(Main.w.getContentPane().getSize());

    if (objects.size()>0)
    {
      for (int i = 0; i < objects.size(); i++)
      {
        objects.get(i).update();
        objects.get(i).checkCollisions(objects);
      }


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

        if (prediction.size() == 0 && objects.get(ID).isDragging)
        {
          predict((int) objects.get(ID).xVel, (int) objects.get(ID).yVel);
        }
        else if (prediction.size() > 0 && !objects.get(ID).isDragging)
          prediction.remove(0);
        time_elapsed = 5;
      }
    }

  }

  public void mouseClicked()
  {
    objects.get(objects.size()-1).isDragging = false;
  }

  public void mousePressed()
  {
    objects.add(new KinematicObject(Window.x, Window.y));
    trail = new ArrayList<Point>(); // reset trail
    prediction = new ArrayList<Point>(); // reset prediction
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

      predict((int) obj.xVel, (int) obj.yVel);
    }
  }

  public int calculateHorizontal(int xVel, int time)
  {
    // delta Xx = V * t
    return (int) (xVel * time);
  }

  public int calculateVertical(int yVel, int time)
  {
    // delta Xx = Vit + 1/2at^2
    return (int) (yVel * time - 0.5 * KinematicObject.g * time * time);
  }

  public void predict(int xVel, int yVel)
  {
    KinematicObject obj = objects.get(objects.size()-1);
    for (int i = 0; i < points; i++)
    {
      int newX = calculateHorizontal((int) xVel, i*5); //i*k, where k = distance apart of each predicted point
      int newY =calculateVertical((int) yVel, i*5); // must be the same for both
      if (prediction.size() == points)
        prediction = new ArrayList<Point>();

      prediction.add(new Point(obj.x + newX, obj.y + newY));
    }
  }

}
