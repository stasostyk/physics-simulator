package Engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Point;

public class CalorimetryProcessor
{
  private int mIce, mWater = 0;
  private int tWater = 10;
  private int capIce = 2200; // J/kgK
  private int latIce = 334000; // J/kg
  private int capWater = 4200; // J/kgK

  private int waterArea = 0;
  private ArrayList<Integer[]> streams = new ArrayList<Integer[]>();
  private ArrayList<Point> cubes = new ArrayList<Point>();
  private ArrayList<Integer> masses = new ArrayList<Integer>();

  private boolean isPouring = false;
  private boolean isDragging = false;

  // minimum water required to melt the ice
  public double requiredWaterMass()
  {
    return (double)(mIce*latIce) / (capWater*tWater);
  }

  public int getHeight()
  {
    return (int)((-100 + Math.sqrt(10000+(2.0/3)*waterArea))/(1.0/3));
  }

  public CalorimetryProcessor()
  {

  }

  public void draw(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    int centX = Window.w/2;

    // draw water faucet
    g.setColor(Color.darkGray);
    g.fillRect(centX-10, Window.h-350, centX+10, 25);
    g.fillRect(centX-10, Window.h-350, 25, 50);

    // draw water pouring
    g.setColor(new Color(173, 216, 230));
    for (int i = 0; i < streams.size(); i++)
    {
      g.fillRect(centX-10, Window.h-300+streams.get(i)[0], 25, streams.get(i)[1]);
    }

    // draw container
    g2.setStroke(new BasicStroke(10));
    g.setColor(Color.BLACK);
    g.drawLine(centX-50, Window.h-5, centX-75, Window.h-155);
    g.drawLine(centX-50, Window.h-5, centX+50, Window.h-5);
    g.drawLine(centX+50, Window.h-5, centX+75, Window.h-155);

    // draw container water
    g.setColor(new Color(183, 220, 235));
    int xpoints[] = {centX-50, centX-50-getHeight()/6, centX+50+getHeight()/6, centX+50};
    int ypoints[] = {Window.h-10, Window.h-10-getHeight(), Window.h-10-getHeight(), Window.h-10};
    g.fillPolygon(xpoints, ypoints, 4);

    // draw cube reserve
    g.setColor(new Color(45,245, 245));
    g.fillRect(50, Window.h-35, 30, 30);

    // draw moving cube
    if (isDragging)
    {
      g.setColor(new Color(0, 235, 235));
      g.fillRect(Window.x-15, Window.y-15, 30, 30);
    }

    // draw cubes
    for (int i = 0; i < cubes.size(); i++)
    {
      g.fillRect(cubes.get(i).x, cubes.get(i).y, 30, 30);
    }

  }

  public void update()
  {
    if (streams.size() > 0)
    {
      int speed = 4;
      int last_ind = streams.size()-1;
      if (isPouring)
      {
        Integer[] new_stream = {streams.get(last_ind)[0], streams.get(last_ind)[1] + speed};
        streams.set(last_ind, new_stream);
      } else
      {
        Integer[] new_stream = {streams.get(last_ind)[0] + speed, streams.get(last_ind)[1]};
        streams.set(last_ind, new_stream);
      }

      if (getHeight() >= 150)
      {
        this.isPouring = false;
      }
      else if (streams.get(0)[0] + streams.get(0)[1] >= 295 - getHeight())
      {
        waterArea += 50;
      }
      // System.out.println(streams.size());

      if (streams.get(0)[0] >= 295 - getHeight())
        streams.remove(0);

      for (int i = 0; i < streams.size()-1; i++)
      {
        Integer[] new_stream = {streams.get(i)[0] + speed, streams.get(i)[1]};
        streams.set(i, new_stream);
      }

    }
  }

  public void mousePressed()
  {
    if (Window.x >= 50 && Window.x <= 80 && Window.y < Window.h-5 && Window.y > Window.h-35)
      isDragging = true;
    else
    {
      this.isPouring = true;
      if (isPouring && getHeight() < 150)
      {
        Integer[] new_stream = new Integer[]{0,0};
        streams.add(new_stream);
      }
    }
  }

  public void mouseReleased()
  {
    int centX = Window.w/2;
    int neededX = (getHeight()-15)/6;
    // System.out.println(Window.y + " : " + getHeight());
    if (isDragging && Window.h-Window.y >= 25
                    && Window.h-Window.y-10 <= getHeight()
                    && Window.x-15 >= centX-50-neededX
                    && Window.x+15 <= centX+50+neededX)
    {
      cubes.add(new Point(Window.x-15, Window.y-15));
      masses.add(30);
    }

    isDragging = false;
    isPouring = false;
  }


}
