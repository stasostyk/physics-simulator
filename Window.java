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
import java.util.ArrayList;

public class Window extends JPanel
                  implements ActionListener, MouseListener, KeyListener
{
  private int time;
  private Font font = new Font("TimesRoman", Font.PLAIN, 20);

  private KinematicProcessor kine = new KinematicProcessor();

  private int mode = 0; // 0 = menu, 1 = kinematics

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
        g.setColor(Color.BLACK);
        g.drawString("For kinematics, press 1", 150, 130);
        break;
      }

      case (1):
      {
        kine.draw(g);
        break;
      }
    }

  }

  public void actionPerformed(ActionEvent e)
  {
    time++;

    switch (mode)
    {
      case (1):
        kine.update();
    }

    repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
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
        kine.mousePressed(e);
    }
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    switch (mode)
    {
      case (1):
        kine.mouseReleased(e);
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
