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

public class DynamicProcessor
{
  private int mouseX = 0;
  private int mouseY = 0;

  private boolean isDragging = false;

  private double slope;
  private int x, y;

  private DynamicObject obj;
  private Window win;

  public Slider[] sliders;

  public DynamicProcessor(Window win)
  {
    this.win = win;
    obj = new DynamicObject(10,25,0.5,0.4,0); // mass, angle, muS, muK, Fapp

    sliders = new Slider[] {new Slider(1, 40, 10), new Slider(5,45,25), new Slider(-300,300,0)};
    sliders[0].value = 10;
    sliders[1].value = 25;
    sliders[2].value = 0;
  }

  public void setIncline()
  {
    // adj * tan (angle) = opp
    this.x = 0;
    this.y = Window.h - (int) (Window.w * Math.tan(obj.angle));
  }

  public void draw(Graphics g)
  {
    g.setColor(Color.RED);
    g.drawLine(Window.x, 0, Window.x, Window.h);
    g.drawLine(0, findPoint(Window.x), Window.w, findPoint(Window.x));

    g.setColor(Color.GREEN);
    g.fillPolygon(new int[] {x, x, Window.w}, new int[] {y, Window.h, Window.h}, 3); // inclined plain

    g.setColor(Color.BLACK);
    g.drawString("Mass = " + sliders[0].value + " kg", Window.w/2-150,20);
    g.drawString("Angle = " + sliders[1].value + " deg", Window.w/2-150,40);
    g.drawString("F app = " + sliders[2].value + " N", Window.w/2-150,60);

    if (obj.isDragging)
    {
      g.setColor(Color.GRAY);

      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(3));
      g2.draw(rotateRectangle(Window.x, findPoint(Window.x)-10, 50, 20, obj.angle));
    }
    else
    {
      // Fnorm line coords
      int xPos, yPos;
      xPos = obj.x + (int)(obj.Fnorm * Math.sin(obj.angle));
      yPos = obj.y - 10 - (int)(obj.Fnorm * Math.cos(obj.angle));

      g.setColor(Color.BLACK);

      String acc = String.format("%.3f", obj.accel); // rounding to three decimal points
      g.drawString("a = " + acc + " m/s^2", xPos+10, obj.y-130);

      String vel = String.format("%.1f", obj.vel);
      g.drawString("v = " + vel + " m/s", xPos+10, obj.y-110);

      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(3));
      g2.draw(rotateRectangle(obj.x, obj.y-10, 50, 20, obj.angle));

      g.setColor(Color.BLUE);

      // Fnorm line
      g.drawString("Fn = " + (int) obj.Fnorm, xPos, yPos);
      g.drawLine(obj.x, obj.y - 10, xPos, yPos);

      // Fgrav line
      xPos = obj.x;
      yPos = obj.y - 10 + (int)(obj.Fgrav);
      g.drawString("Fg = -" + (int) obj.Fgrav, obj.x+5, obj.y+30);
      g.drawLine(obj.x, obj.y - 10, xPos, yPos);

      // Fapp line
      xPos = obj.x + (int)(obj.Fapp * Math.cos(obj.angle));
      yPos = obj.y - 10 + (int)(obj.Fapp * Math.sin(obj.angle));
      g.drawString("F = " + (int) obj.Fapp, xPos, yPos);
      g.drawLine(obj.x, obj.y - 10, xPos, yPos);

      // Ffric line
      xPos = obj.x + (int)(obj.Ffric * Math.cos(obj.angle));
      yPos = obj.y - 10 + (int)(obj.Ffric * Math.sin(obj.angle));
      g.drawString("f = " + (int) obj.Ffric, xPos, yPos);
      g.drawLine(obj.x, obj.y - 10, xPos, yPos);

    }
  }

  public Shape rotateRectangle(int x, int y, int width, int height, double theta)
  {
    Rectangle2D rect = new Rectangle2D.Double(-width/2., -height/2., width, height);

    AffineTransform transform = new AffineTransform();
    transform.translate(x, y);
    transform.rotate(theta);

    return transform.createTransformedShape(rect);
  }

  public int findPoint(int xVal) // y=mx+b, returns the y counterpart of the coordinate on the inclined surface
  {
    return (int)(((Window.h-y)/(double)Window.w) * xVal) + y;
  }

  public void setup()
  {
    setIncline();
    obj.x = Window.w/2;
    obj.y = findPoint(Window.w/2);
  }

  public void update()
  {
    this.mouseX = Window.x;
    this.mouseY = Window.y;

    obj.update();

    if (sliders[0].value != obj.mass)
      obj.updateMass(sliders[0].value);

    if (sliders[1].value != obj.angle)
    {
      obj.updateAngle(Math.toRadians(sliders[1].value));
      setIncline();

      obj.y = findPoint(obj.x);
    }

    if (sliders[2].value != obj.Fapp)
    obj.updateFapp(sliders[2].value);
}

  public void mouseClicked()
  {
  }

  public void mousePressed()
  {
    System.out.println(obj);
    obj.isDragging = true;
  }

  public void mouseReleased()
  {
    obj.isDragging = false;

    obj.x = Window.x;
    obj.y = findPoint(Window.x);
    obj.vel = 0;
  }

  public class Slider
  {
    private JSlider slider;
    public int value;
    public Slider(int min, int max, int init)
    {
      slider = new JSlider(JSlider.VERTICAL, min, max, init);
      slider.addChangeListener(new ChangeListener()
      {
        public void stateChanged(ChangeEvent e)
        {
          value = slider.getValue();
        }
      });
    }
    public JSlider getSlider()
    {
      return slider;
    }
  }

}
