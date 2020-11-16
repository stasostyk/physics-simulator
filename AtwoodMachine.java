public class AtwoodMachine
{
  public static double g = 0.5;

  public double tens, accel, vel; // force of tension and acceleration, both are shared among two obj
  // in this simulation, the pulley is frictionless and massless

  public int disp = 0; // displacement of weight from OG position

  Weight one; // heavier one
  Weight two;

  public int direction;

  public AtwoodMachine(int m1, int m2)
  {
    direction = 1;

    if (m1 >= m2)
    {
      one = new Weight(m1, -1);
      two = new Weight(m2, 1);
    } else
    {
      one = new Weight(m2, -1);
      two = new Weight(m1, 1);
    }

    this.accel = findAccel(one, two);
    this.tens = findTension(one); // both one and two SHOULD return the same value
    this.vel = 0;
  }

  public void update()
  {
    vel += accel;
    disp += vel;
  }

  public double findAccel(Weight one, Weight two) // assumes one as the heavier object
  {
    double acc = (one.mass * g - two.mass * g) / (one.mass + two.mass); // a = (Mg - mg) / (m + M), derived from
    // T = mg + ma, T = Mg - Ma (where M is the mass of the heavier object)
    // then substitute to solve for acceleration

    return acc * direction;
  }

  public double findTension(Weight w)
  {
    double tension = w.mass * g + w.mass * this.accel * w.dir; // derived from formula Ft - mg = ma, or F = ma (second law)
    return tension;
  }

  public class Weight // the individual weights on each side
  {
    public int mass, dir; // dir up = 1, down = -1
    public int x, y = 0; // coordinates

    public Weight(int m, int d)
    {
      this.mass = m;
      this.dir = d;
    }

  }

  public void updateMass(int m1, int m2)
  {
    if (m1 >= m2)
    {
      one = new Weight(m1, -1);
      two = new Weight(m2, 1);
    } else
    {
      one = new Weight(m2, -1);
      two = new Weight(m1, 1);
    }

    this.accel = findAccel(one, two);
    this.tens = findTension(one); // both one and two SHOULD return the same value
  }

  public String toString()
  {
    return "a -> " + accel + ", v -> " + vel + ", d -> " + disp;
  }
}
