public class AtwoodMachine
{
  public static double g = 9.81;

  private double tens, accel, vel; // force of tension and acceleration, both are shared among two obj
  // in this simulation, the pulley is frictionless and massless

  Weight one; // heavier one
  Weight two;

  public AtwoodMachine(int m1, int m2)
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
    this.vel = 0;
  }

  public void update()
  {
    vel += accel;
  }

  public double findAccel(Weight one, Weight two) // assumes one as the heavier object
  {
    double acc = (one.mass * g - two.mass * g) / (one.mass + two.mass); // a = (Mg - mg) / (m + M), derived from
    // T = mg + ma, T = Mg - Ma (where M is the mass of the heavier object)
    // then substitute to solve for acceleration

    return acc;
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
}
