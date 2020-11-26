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
  private int compression = 0;
  private boolean isTouching = false;


  private Ball b;

  public SpringProcessor()
  {
    b = new Ball(10);
    SprConst = 1.3; // N/m
  }

  public void draw(Graphics g)
  {
    int cX = Window.w/2;
    int cY = 500;

    g.setColor(new Color(50,20,160));
    g.fillOval(Window.w/2 - 50, 100 + b.disp, 100,100);

    g.setColor(Color.GRAY);
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(3));

    g.drawOval(cX-50, cY-50-compression, 100, 50+compression);
    g.drawOval(cX-50, cY-100-2*compression, 100, 50+compression);
    g.drawOval(cX-50, cY-150-3*compression, 100, 50+compression);
    g.drawOval(cX-50, cY-200-4*compression, 100, 50+compression);

  }

  public void update()
  {
    int y = (int)(100 + b.disp + b.vel + 0.5);
    int top = 500 - (500-200-4*compression);
    if (y > top)
    {
      isTouching = true;
      compression = (top - y)/10;
    }
    else isTouching = false;

    // if (compression > -25) compression--;
    // if (compression > -25) compression--;
    // if (y+b.vel < Window.h)
    b.update();
  }

  public class Ball
  {
    private int mass;
    public double vel = 0;
    public int disp = 0;

    public Ball(int m)
    {
      mass = m;
    }

    public void update()
    {
      if (isTouching)
      {
        // Fs = -k * x
        double Fs = -SprConst*Math.abs(compression*10);
        double Fg = b.mass * g;
        // Fs + Fg = m * a --> a = Fnet/m
        double res = (Fg+Fs)/b.mass;
        vel += res;
        System.out.println(res);
      } else
      {
        vel += g;
      }
      disp += (int)(vel+0.5);
    }
  }

}
