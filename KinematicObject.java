import java.util.ArrayList;

public class KinematicObject
{
  public static double g = -1;
  public int x, y = 0;
  public double iniV, angl = 0; // angle is meassured North of East (from the horizontal)
  public boolean isDragging;

  public int xVel, yVel = 0;

  public double bounciness = 2;
  private int R = 10; // radius

  KinematicObject(int x, int y)
  {
    this.x = x;
    this.y = y;
    isDragging = true;
  }

  public void update() // assumes one second has passed
  {
    int w = Window.w;
    int h = Window.h;
    if (!isDragging && y<h-R-yVel)
    {
      yVel-=g;
    }
    // System.out.println(yVel);

    // if (yVel == 0) xVel = 0;

    if (x <= R || x >= w-R)
    {
      // System.out.println(".");
      // if (xVel < 0) xVel += bounciness;
      // else if (xVel > 0) xVel -= bounciness;

      xVel = -xVel;
    }

    if (y <= R-yVel || y >= h-R-yVel)
    {
      if (yVel < 0) yVel += bounciness;
      else if (yVel > 0) yVel -= bounciness;

      yVel = -yVel;
      return;
    }

    // System.out.println(xVel + " " + yVel);

    y += yVel;
    x += xVel;

  }

  public void setInitialVelocity(double xDrag, double yDrag) // x and y component vectors
  {
    angl = Math.toDegrees(Math.atan2(xDrag, yDrag));
    if (angl < 0)
      angl+=360; // sets angle to degrees between 0 and 360
    angl -= 90;
    if (angl < 0)
      angl += 360; // sets orientation to East
    angl = Math.abs(360-angl); // sets it counter clockwise
    iniV = Math.hypot(xDrag, yDrag); // sets resultant vector

    xVel = (int)(-xDrag/10);
    yVel = (int)(yDrag/10);

  }

  public void checkCollisions(ArrayList<KinematicObject> others)
  {
    for (int i = 0; i < others.size(); i++)
    {
      KinematicObject obj = others.get(i);

      if (obj.x == x && obj.y == y)
        continue;

      if (x - 2*R < obj.x && obj.x < x + 2*R && y - 2*R < obj.y && obj.y < y + 2*R)
      {
        if (xVel < 0) xVel += bounciness;
        else if (xVel > 0) xVel -= bounciness;
        if (yVel < 0) yVel += bounciness;
        else if (yVel > 0) yVel -= bounciness;
        yVel = -yVel;
        xVel = -xVel;
      }

    }
  }
}
