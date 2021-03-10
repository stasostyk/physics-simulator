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

public class SpringProcessor
{
  private final double g = 1.5;
  private double SprConst;
  private int len, mass;
  private double compression = 0;
  private boolean isTouching = false;

  private double edgeVel = 0;

  private Ball b;

  public SpringProcessor()
  {
    b = new Ball(10);
    SprConst = 10; // N/m
  }

  public void draw(Graphics g)
  {
    int cX = Window.w/2;
    int cY = 500;

    g.setColor(new Color(50,20,160));
    g.fillOval(Window.w/2 - 50, 100 + (int)(b.disp+0.5), 100,100);

    g.setColor(Color.GRAY);
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(3));

    g.drawOval(cX-50, cY-50-(int)compression, 100, 50+(int)compression);
    g.drawOval(cX-50, cY-100-(int)(2*compression), 100, (int)(50+compression));
    g.drawOval(cX-50, cY-150-(int)(3*compression), 100, (int)(50+compression));
    g.drawOval(cX-50, cY-200-(int)(4*compression), 100, (int)(50+compression));

  }

  public void update()
  {
    b.update();
    double y = 100 + (int)(b.disp+0.5) + b.vel;
    double top = 500 - (500-200-4*compression);
    if (y > top)
    {
      if (isTouching == false)
      {
        edgeVel = -b.vel;
      }
      isTouching = true;
      compression = (top - y)/10;
    }
    else
    {
      if (isTouching == true)
      {
        System.out.println("left");
        b.vel = edgeVel - g;
        b.update();
        isTouching = false;
        b.update();
      }
    }
    // if (compression > -25) compression--;
    // if (compression > -25) compression--;
    // if (y+b.vel < Window.h)
  }

  public class Ball
  {
    private int mass;
    public double vel, disp = 0;
    // public int disp = 0;

    public Ball(int m)
    {
      mass = m;
    }

    public void update()
    {
      if (isTouching)
      {
        // Fs = -k * x
        double Fs = -SprConst*Math.abs(compression);
        double Fg = b.mass * g;
        // Fs + Fg = m * a --> a = Fnet/m
        double res = (Fg+Fs)/(double)b.mass;
        vel += res;
        System.out.println(res);
      } else
      {
        vel += g;
      }
      disp += vel;
    }
  }

}
