import java.util.ArrayList;

public class PhysicsObject
{
  private static double g = -1;
  public int x, y = 0;
  public double iniV, angl = 0; // angle is meassured North of East (from the horizontal)
  public boolean isDragging;

  public double xVel, yVel = 0;

  public double bounciness = 2;

  PhysicsObject(int x, int y)
  {
    this.x = x;
    this.y = y;
    isDragging = true;
  }

  public void update() // assumes one second has passed
  {
    if (!isDragging && y<455-yVel)
    {
      yVel-=g;
      // System.out.println(angl);
    }

    if (x <= 10-xVel || x >= 490-xVel)
    {
      if (xVel < 0) xVel += bounciness;
      else if (xVel > 0) xVel -= bounciness;

      xVel = -xVel;
    }

    if (y <= 0-yVel || y >= 455-yVel)
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

    xVel = -xDrag/10;
    yVel = yDrag/10;

  }

  public void checkCollisions(ArrayList<PhysicsObject> others)
  {
    for (int i = 0; i < others.size(); i++)
    {
      PhysicsObject obj = others.get(i);

      if (obj.x == x && obj.y == y)
        continue;

      if (x - 23 < obj.x && obj.x < x + 23 && y - 23 < obj.y && obj.y < y + 23)
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
