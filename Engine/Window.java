package Engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
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
import javax.swing.JFrame;

public class Window extends JPanel
                  implements ActionListener, MouseListener, KeyListener
{
  private int time;
  private Font font = new Font("TimesRoman", Font.PLAIN, 20);

  // seting up different modes
  private KinematicProcessor kine = new KinematicProcessor();
  private DynamicProcessor dyna = new DynamicProcessor(this); // pass this to allow use of JSlider
  private AtwoodProcessor atw = new AtwoodProcessor(this);
  private IdealGasProcessor idealgas = new IdealGasProcessor();
  private CalorimetryProcessor calo = new CalorimetryProcessor();


  private int mode = 0; // 0 = menu, 1 = kinematics, 2 = dynamics, 3 = atwood, 4 = ideal gas

  public static int w, h = 500; // default

  public static int x, y = 0; // default

  private JFrame frame;

  public Window(JFrame frame)
  {
    time = 0;
    Timer clock = new Timer(30, this);
    clock.start();

    this.frame = frame;
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
        g.drawString("Which mode?", (w/2)-50, (h/4));
        g.setColor(Color.GRAY);
        g.fillRect((w/2)-100,(h/2)-50,200,30);
        g.fillRect((w/2)-100,(h/2)-10,200,30);
        g.fillRect((w/2)-100,(h/2)+30,200,30);
        g.fillRect((w/2)-100,(h/2)+70,200,30);
        g.fillRect((w/2)-100, (h/2)+110, 200, 30);
        g.setColor(Color.BLACK);
        g.drawString("Kinematics: press 1", (w/2)-90, (h/2)-30);
        g.drawString("Inclined planes: press 2", (w/2)-90, (h/2)+10);
        g.drawString("Atwood +: press 3", (w/2)-90, (h/2)+50);
        g.drawString("Ideal gas: press 4", (w/2)-90, (h/2)+90);
        g.drawString("Calorimetry: press 5", (w/2)-90, (h/2)+130);
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
      case (3):
      {
        atw.draw(g);
        break;
      }
      case (4):
      {
        idealgas.draw(g);
        break;
      }
      case (5):
      {
        calo.draw(g);
        break;
      }
    }

  }

  public void actionPerformed(ActionEvent e)
  {
    time++;

    // Main.w.pack();

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
      case (3):
      {
        atw.update();
        break;
      }
      case (4):
      {
        idealgas.update();
        break;
      }
      case (5):
      {
        calo.update();
        break;
      }
    }

    repaint();
    this.w = (int) this.frame.getContentPane().getSize().getWidth();
    this.h = (int) this.frame.getContentPane().getSize().getHeight();
    // System.out.println(this.w);
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
      case(4):
      {
        idealgas.mouseClicked();
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
      {
        kine.mousePressed();
        break;
      }
      case (2):
      {
        dyna.mousePressed();
        break;
      }
      case (4):
      {
        idealgas.mousePressed();
        break;
      }
      case (5):
      {
        calo.mousePressed();
        break;
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    switch (mode)
    {
      case (1):
      {
        kine.mouseReleased();
        break;
      }
      case (2):
      {
        dyna.mouseReleased();
        break;
      }
      case (4):
      {
        idealgas.mouseReleased();
        break;
      }
      case (5):
      {
        calo.mouseReleased();
        break;
      }
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
        else if (e.getKeyCode() == e.VK_2)
        {
          this.setLayout(new FlowLayout());
          dyna.setup();
          this.mode = 2;
          for (int i = 0; i < dyna.sliders.length; i++)
          {
            this.add(dyna.sliders[i].getSlider());
          }
          this.updateUI();
        } else if (e.getKeyCode() == e.VK_3)
        {
          this.mode = 3;
          this.setLayout(new FlowLayout(FlowLayout.LEFT));
          for (int i = 0; i < atw.sliders.length; i++)
          {
            this.add(atw.sliders[i].getSlider());
          }
          this.updateUI();
        } else if (e.getKeyCode() == e.VK_4)
        {
          this.mode = 4;
        } else if (e.getKeyCode() == e.VK_5)
        {
          this.mode = 5;
        }
      }
      return;
    }

    if (e.getKeyCode() == e.VK_ESCAPE)
    {
      mode = 0;
      for (int i = 0; i < dyna.sliders.length; i++)
      {
        this.remove(dyna.sliders[i].getSlider());
      }
      for (int i = 0; i < atw.sliders.length; i++)
      {
        this.remove(atw.sliders[i].getSlider());
      }
      this.updateUI();
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
