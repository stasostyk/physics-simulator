import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import java.util.ArrayList;

public class DynamicProcessor
{
  private int mouseX = 0;
  private int mouseY = 0;

  private boolean isDragging = false;

  private double slope;
  private int x, y;

  private DynamicObject obj;
  private Window win;

  public DynamicProcessor(Window win)
  {
    this.win = win;
    obj = new DynamicObject(10,15,0.5,0.4,30); //mass, angle, muS, muK, Fapp
    setIncline();
  }

  public void setIncline()
  {
    // adj * tan (angle) = opp
    // Right now, its limited between ]0,45] degrees.
    // To give compatability for ]45,90[, change origin and move second coords
    this.x = 0;
    this.y = 500 - (int) (500.0 * Math.tan(obj.angle));
  }

  public void draw(Graphics g)
  {
    g.setColor(Color.GREEN);
    System.out.println("x: " + mouseX + ", y: " + mouseY);
    g.drawLine(x,y,mouseX,mouseY);
  }

  public void update()
  {
    this.mouseX = Window.x;
    this.mouseY = Window.y;
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

  private class Slider
  {
    private JSlider slider;
    public Slider()
    {
      slider = new JSlider();
    }
  }

}
