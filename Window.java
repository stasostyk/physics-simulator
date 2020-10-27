import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.awt.MouseInfo;
import java.awt.Point;

public class Window extends JPanel
                  implements ActionListener, MouseListener, KeyListener
{
  private int time;
  private Font font = new Font("TimesRoman", Font.PLAIN, 20);

  private KinematicProcessor kine = new KinematicProcessor();

  private DynamicProcessor dyna = new DynamicProcessor(this); // pass this to allow use of JSlider

  private int mode = 0; // 0 = menu, 1 = kinematics, 2 = dynamics

  public static int w, h = 500; // default

  public static int x, y = 0; // default

  public Window()
  {
    time = 0;
    Timer clock = new Timer(30, this);
    clock.start();
  }

  public void paintComponent(Graphics g)
  {
    setBackground(Color.WHITE);
    g.setFont(font);
    super.paintComponent(g);

    switch(mode)
    {
      case (0):
      {
        g.drawString("Which mode?", 180, 100);
        g.setColor(Color.GRAY);
        g.fillRect(140,110,200,30);
        g.fillRect(140,130,200,30);
        g.setColor(Color.BLACK);
        g.drawString("For kinematics, press 1", 150, 130);
        g.drawString("For dynamics, press 2", 150, 150);
        break;
      }

      case (1):
      {
        kine.draw(g);
        break;
      }

      case (2):
      {
        dyna.draw(g);
        break;
      }
    }

  }

  public void actionPerformed(ActionEvent e)
  {
    time++;

    this.w = (int) Main.w.getContentPane().getSize().getWidth();
    this.h = (int) Main.w.getContentPane().getSize().getHeight();

    Point p = MouseInfo.getPointerInfo().getLocation();
    SwingUtilities.convertPointFromScreen(p, this);
    this.x = p.x;
    this.y = p.y;


    switch (mode)
    {
      case (1):
      {
        kine.update();
        break;
      }
      case (2):
      {
        dyna.update();
        break;
      }
    }

    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
    switch (mode)
    {
      case(1):
      {
        kine.mouseClicked();
        break;
      }
    }
  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
  }

  @Override
  public void mouseExited(MouseEvent e)
  {}

  @Override
  public void mousePressed(MouseEvent e)
  {
    switch (mode)
    {
      case (1):
        kine.mousePressed();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    switch (mode)
    {
      case (1):
        kine.mouseReleased();
    }
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    switch (mode)
    {
      case (0):
      {
        if (e.getKeyCode() == e.VK_1) this.mode = 1;
        else if (e.getKeyCode() == e.VK_2) this.mode = 2;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {

  }

  @Override
  public void keyTyped(KeyEvent e)
  {}

}
