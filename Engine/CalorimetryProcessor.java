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
import java.awt.Point;

public class CalorimetryProcessor
{
  private int mIce = 0;
  private double tWater = 60;
  private double capIce = 2.2; // J/kgK
  private double latIce = 334; // J/kg
  private double capWater = 4.2; // J/kgK
  private double Tf = 0;

  private int waterArea = 0;
  private ArrayList<Integer[]> streams = new ArrayList<Integer[]>();
  private ArrayList<Point> cubes = new ArrayList<Point>();
  private ArrayList<Integer> masses = new ArrayList<Integer>();

  private boolean isPouring = false;
  private boolean isDragging = false;
  private String reaction = "none";
  private double reactQ = 0;

  public int getHeight()
  {
    return (int)((-100 + Math.sqrt(10000+(2.0/3)*waterArea))/(1.0/3));
  }

  public void startReaction()
  {
    if (mIce*latIce > waterArea*capWater*tWater)
    {
      // not all of the ice will melt, only some
      Tf = 0;
      double iceMelted = (waterArea*capWater*tWater)/(double)latIce;

      reactQ = mIce-iceMelted;
      reaction = "some";

    } else if (mIce*latIce == waterArea*capWater*tWater)
    {
      // exactly all the ice will melt
      Tf = 0;
      reaction = "all";

    } else
    {
      // all ice will melt, then heat up
      Tf = (waterArea*capWater*tWater-mIce*latIce)/(double)(capWater*mIce+capWater*waterArea);
      tWater = Tf;
      reaction = "all";
    }
  }

  public CalorimetryProcessor()
  {
  }

  public void draw(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    int centX = Window.w/2;

    // draw button
    g.setColor(Color.RED);
    g.fillRect(10, 10, 30,30);
    g.drawString("Begin Reaction", 50, 30);

    g.setColor(Color.BLUE);
    g.fillRect(10,50,30,30);
    g.drawString("Reheat Water", 50, 70);

    // variable text
    g.setColor(Color.BLACK);
    g.drawString("T water = " + (((int)((tWater)*100))/100.0) + "C", 15, 110);
    g.drawString("T ice = 0C", 15, 140);
    g.drawString("T final = " + (((int)((Tf)*100))/100.0) + "C", 15, 170);
    g.drawString("Ice mass = " + mIce + "g", 15, 190);
    g.drawString("Water mass = " + waterArea + "g", 15, 220);

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
    g.fillRect(15, Window.h-35, 30, 30);
    g.drawString("Drag the ice", 15, Window.h-55);

    // draw moving cube
    if (isDragging)
    {
      g.setColor(new Color(0, 235, 235));
      g.fillRect(Window.x-15, Window.y-15, 30, 30);
    }

    // draw cubes
    for (int i = 0; i < cubes.size(); i++)
    {
      int size = (int)Math.sqrt(masses.get(i));
      g.fillRect(cubes.get(i).x + 15 - size/2, cubes.get(i).y + 15 - size/2, size, size);
    }

  }

  public void update()
  {
    // water mechanics
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

    // reaction
    int mass = 0;
    for (int i = 0; i < masses.size(); i++)
      mass += masses.get(i);
    this.mIce = mass;

    if (reaction.equals("all"))
    {
      for (int i = 0; i < masses.size(); i++)
      {
        if (masses.get(i) <= 0)
        {
          cubes.remove(i);
          masses.remove(i);
          // mIce = 0;
        } else
        {
          mIce -= 50;
          masses.set(i, masses.get(i)-50);

          if (waterArea < 18700)
            waterArea += 50;
        }

        if (masses.size() == 0)
          reaction = "none";
      }
    } else if (reaction.equals("some"))
    {
      if (reactQ <= mIce)
      {
        reaction = "none";
      }
      masses.set(0, masses.get(0)-50);
      mIce -= 50;
      if (masses.get(0) <= 0)
      {
        cubes.remove(0);
        masses.remove(0);
      }
    }
  }

  public void mousePressed()
  {
    if (Window.x >= 15 && Window.x <= 45 && Window.y < Window.h-5 && Window.y > Window.h-35)
      isDragging = true;
    else if (Window.x >= 10 && Window.x <= 40 && Window.y >= 40 && Window.y <= 70)
      tWater = 60;
    else if (cubes.size() > 0 && reaction.equals("none") && !isPouring && Window.x >= 10 && Window.x <= 40 && Window.y >= 10 && Window.y <= 40)
      startReaction();
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
                    && Window.x+15 <= centX+50+neededX
                    && reaction.equals("none"))
    {
      cubes.add(new Point(Window.x-15, Window.y-15));
      masses.add(900);
      mIce += 900;
    }

    isDragging = false;
    isPouring = false;
  }


}
