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
    obj = new DynamicObject(this, 10,30,0.5,0.4,30); // processor, mass, angle, muS, muK, Fapp

    sliders = new Slider[] {new Slider(1, 20, 10), new Slider(5,45,25)};
    sliders[1].value = 25;
    sliders[0].value = 10;
  }

  public void setIncline()
  {
    // adj * tan (angle) = opp
    // Right now, its limited between ]0,45] degrees.
    // To give compatability for ]45,90[, change origin and move second coords
    this.x = 0;
    this.y = Window.h - (int) (Window.w * Math.tan(obj.angle));
    // System.out.println(Window.w);
  }

  public void draw(Graphics g)
  {
    // System.out.println(obj.vel);
    g.setColor(Color.RED);
    g.drawLine(Window.x, 0, Window.x, Window.h);
    g.drawLine(0, findPoint(Window.x), Window.w, findPoint(Window.x));

    g.setColor(Color.BLACK);
    g.drawString("Mass = " + sliders[0].value, Window.w/2-100,40);
    g.drawString("Angle = " + sliders[1].value, Window.w/2+50,40);


    g.setColor(Color.GREEN);
    // System.out.println("x: " + mouseX + ", y: " + mouseY);
    // g.drawLine(x,y,Window.w, Window.h);
    g.fillPolygon(new int[] {x, x, Window.w}, new int[] {y, Window.h, Window.h}, 3); // inclined plain

    g.setColor(Color.BLACK);
    int originX = (Window.w/2) - 20;
    int originY = findPoint(originX);
    // g.fillPolygon(new int[] {originX, originX+10, originX+40, originX+50}, new int[] {}, 4);
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(3));
    g2.draw(rotateRectangle(Window.w/2, findPoint(Window.w/2)-10, 50, 20, obj.angle));
    // g.fillOval(Window.w/2-30, findPoint(Window.w/2-30)-20, 30, 30);



    g.setColor(Color.BLUE);
    // Fnorm line
    g.drawLine(Window.w/2, findPoint(Window.w/2) - 10, Window.w/2 + (int)(obj.Fnorm * Math.sin(obj.angle)), findPoint(Window.w/2)- 10 - (int)(obj.Fnorm * Math.cos(obj.angle)));
    // Fgrav line
    g.drawLine(Window.w/2, findPoint(Window.w/2) - 10, Window.w/2, findPoint(Window.w/2) - 10 + (int)(obj.Fgrav));
    // Fapp line
    g.drawLine(Window.w/2, findPoint(Window.w/2) - 10, Window.w/2 + (int)(obj.Fapp * Math.cos(obj.angle)), findPoint(Window.w/2) - 10 + (int)(obj.Fapp * Math.sin(obj.angle)));
    // Ffric line
    g.drawLine(Window.w/2, findPoint(Window.w/2) - 10, Window.w/2 - (int)(obj.Ffric * Math.cos(obj.angle)), findPoint(Window.w/2) - 10 - (int)(obj.Ffric * Math.sin(obj.angle)));
  }

  public Shape rotateRectangle(int x, int y, int width, int height, double theta)
  {
    // theta = Math.toRadians(theta);

    Rectangle2D rect = new Rectangle2D.Double(-width/2., -height/2., width, height);

    AffineTransform transform = new AffineTransform();
    transform.translate(x, y);
    transform.rotate(theta);

    return transform.createTransformedShape(rect);
  }

  public int findPoint(int xVal)
  {
    return (int)(((Window.h-y)/(double)Window.w) * xVal) + y;
  }

  public void update()
  {
    this.mouseX = Window.x;
    this.mouseY = Window.y;

    obj.updateMass(sliders[0].value+1);
    obj.updateAngle(Math.toRadians(sliders[1].value));

    setIncline();

    obj.update();
}

  public void mouseClicked()
  {

  }

  public void mousePressed()
  {

  }

  public void mouseReleased()
  {

  }

  public class Slider
  {
    private JSlider slider;
    public int value;
    public Slider(int min, int max, int init)
    {
      slider = new JSlider(min, max, init);
      slider.addChangeListener(new ChangeListener()
      {
        public void stateChanged(ChangeEvent e)
        {
          value = slider.getValue();
        }
      });
      // slider.setBounds(0,100,200,120);
    }
    public JSlider getSlider()
    {
      return slider;
    }
  }

}
