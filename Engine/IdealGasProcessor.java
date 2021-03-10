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


public class IdealGasProcessor
{
  private int mouseX = 0;
  private int mouseY = 0;

  public static final double g = 9.81;

  private int width = 150;
  private int depth = 150;

  private double height = 300.0;
  private double new_height = 300.0;

  private int weight = 0;
  private int new_weight = 0;
  private double pressure_change = 1;

  private boolean spawning_weight = false;
  private int numballs = 0;

  private ArrayList<Point> weights = new ArrayList<Point>();
  private ArrayList<Double> speeds = new ArrayList<Double>();

  public void draw(Graphics g)
  {
    int cX = Window.w/2;
    int cY = Window.h/2;


    // draw text
    g.setColor(Color.BLACK);
    g.drawString("Pressure = " + (((int)((pressure_change)*100))/100.0) + " Pa", 10, 30);
    g.drawString("Volume = " + (width*depth*(int)height) + " px^3", 10, 50);
    g.drawString("Displacement = " + (300-(int)height) + " px", 10, 70);
    g.drawString("Weight = " + (int)(weight*this.g) + " N", 10, 90);

    Graphics2D g2 = (Graphics2D) g;

    // preview weight
    g.setColor(Color.MAGENTA);
    if (spawning_weight && mouseX > Window.w/2-65 && mouseX < Window.w/2+65 && mouseY < Window.h-height-30)
    {
      g.fillOval(mouseX-4, mouseY-4, 8, 8);
    }

    // rendering weights
    for (int i = 0; i < weights.size(); i++)
    {
      g.fillOval(weights.get(i).x, weights.get(i).y, 8, 8);
    }

    // draw container
    g.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(10));
    g.drawLine(cX-75, Window.h-20, cX+75, Window.h-20);
    g.drawLine(cX-75, Window.h-20, cX-75, Window.h-320);
    g.drawLine(cX+75, Window.h-20, cX+75, Window.h-320);

    // draw moving surface
    g.setColor(Color.GREEN);
    if (height > new_height && weights.size() == numballs)
    {
      height -= 2;
    }
    g.drawLine(cX-65, Window.h-(int)height-20, cX+65, Window.h-(int)height-20);

    // draw molecules
    g.setColor(Color.BLUE);
    for (int m = 0; m < 30; m++)
    {
      int x = cX-65 + (int)(Math.random()*130);
      int y = Window.h-(int)height-10 + (int)(Math.random()*((int)(height-20)));

      g.fillOval(x, y, 5, 5);
    }

  }

  public void update()
  {
    this.mouseX = Window.x;
    this.mouseY = Window.y;

    // Change of state!
    if (this.weight != this.new_weight)
    {
      double new_pressure_change = pressure_change + (new_weight*g)/(this.depth*this.width); // P = F/A
      double new_volume = ((this.pressure_change)*(this.width*this.depth*this.height))/(new_pressure_change); // Vf = ViPi/Pf

      this.new_height = new_volume/(this.depth*this.width);
      this.weight = this.new_weight;
      this.pressure_change = new_pressure_change;
      System.out.println(this.new_height);

    }

    int count = 0;
    for (int i = 0; i < weights.size(); i++)
    {
      if (weights.get(i).y < Window.h-height-34)
      {
        count++;
        speeds.set(i, speeds.get(i)+0.3);

        int new_point = weights.get(i).y + (int)speeds.get(i).doubleValue();
        if (new_point >= Window.h-height-34)
          weights.get(i).y = Window.h-(int)height-34;
        else
          weights.get(i).y+=(int)speeds.get(i).doubleValue();
      }
    }
    this.numballs = weights.size()-count;
    // System.out.println(numballs);

  }

  public void mouseClicked()
  {
  }

  public void mousePressed()
  {
    if (mouseX > Window.w/2-65 && mouseX < Window.w/2+65 && mouseY < Window.h-height-30)
      this.spawning_weight = true;
  }

  public void mouseReleased()
  {
    if (spawning_weight && mouseX > Window.w/2-65 && mouseX < Window.w/2+65 && mouseY < Window.h-height-30)
    {
      this.new_weight += 100;
      spawning_weight = false;
      weights.add(new Point(mouseX-4, mouseY-4));
      speeds.add(1.0);
    }
  }
}
