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

public class AtwoodProcessor
{
  private AtwoodMachine mach;
  public Slider[] sliders;
  private int prevMass1, prevMass2 = 10;

  public AtwoodProcessor(Window win)
  {
    mach = new AtwoodMachine(10, 10); // mass 1, mass 2 (in Kg)

    sliders = new Slider[] {new Slider(1, 40, 10), new Slider(1,40,10)};
    sliders[0].value = 10;
    sliders[1].value = 10;
  }

  public void draw(Graphics g)
  {
    int cX = Window.w/2;
    int cY = Window.h/2;

    g.setColor(Color.BLACK);
    g.fillOval(cX-20, 20, 40,40);
    g.setColor(Color.CYAN);

    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(3));
    g.drawLine(cX-20, 40, cX-20, 300+(int)mach.disp);
    g.drawLine(cX+20, 40, cX+20, 300-(int)mach.disp);
    g.drawArc(cX-20, 20, 40, 40, 0, 180);

    g.setColor(Color.GRAY);
    g.drawRect(cX-30, 300+(int)mach.disp, 20,20);
    g.drawRect(cX+10, 300-(int)mach.disp, 20,20);
    // System.out.println(mach);
  }

  public void update()
  {

    if (sliders[0].value >= sliders[1].value)
      mach.direction = 1;
    else
      mach.direction = -1;

    if (sliders[0].value != prevMass1 || sliders[1].value != prevMass2)
    {
      mach.updateMass(sliders[0].value, sliders[1].value);
    }

    prevMass1 = sliders[0].value;
    prevMass2 = sliders[1].value;

    mach.update();
    if (Math.abs(mach.disp) >= 150)
    {
      mach.vel = 0;
      if (mach.disp < 0)
        mach.disp = -150;
      else
        mach.disp = 150;
    }
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

}
