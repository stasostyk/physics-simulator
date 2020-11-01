public class DynamicObject
{
  public static double g = 2;
  public double mass, angle, muS, muK; // mass, angle, static coefficient of friction, kinetic coefficient of friction
  public double Fapp, Fnorm, Fgrav, Ffric; // applied, normal, gravitational, and friction force
  public double vel, accel;
  public int x, y = 0;

  public boolean isDragging = false;

  private DynamicProcessor proc;

  public DynamicObject(DynamicProcessor proc, double m, double a, double muS, double muK, double Fapp)
  {
    this.mass = m;
    this.angle = Math.toRadians(a);
    this.muS = muS;
    this.muK = muK;
    this.Fapp = Fapp;
    this.vel = 0;

    this.proc = proc;

    System.out.println("mass = " + mass + ", angle = " + angle);


    this.Fgrav = getFgrav();
    System.out.println("grav: "+Fgrav);
    this.Fnorm = getFnorm();
    System.out.println("norm: "+Fnorm);

    this.Ffric = getFriction();
    System.out.println("fric: "+Ffric);

    this.accel = getAccel();
    System.out.println("accel: "+accel);
  }

  public double getFgrav()
  {
    return mass*g;
  }

  public double getFnorm()
  {
    return Fgrav*Math.cos(angle);
  }

  public double getFriction()
  {
    if (Fgrav*Math.sin(angle) + Fapp > muS * Fnorm) // its moving
      return muK * Fnorm;
    return muS * Fnorm; // its not moving
  }

  public double getAccel()
  {
    if (Fgrav*Math.sin(angle) + Fapp <= muS * Fnorm) return 0;
    return (Fapp + Fgrav*Math.sin(angle) + Ffric) / mass;   // Fnet = m * a
  }

  public void update()
  {
    this.Ffric = getFriction();
    vel += 0.5*accel;
    move();
    // System.out.println(accel);
  }

  public void updateFapp(double newFapp)
  {
    this.Fapp = newFapp;
    this.accel = getAccel();
  }

  public void updateMass(double newMass)
  {
    this.mass = newMass;

    this.Fgrav = getFgrav();
    this.Fnorm = getFnorm();

    this.Ffric = getFriction();

    this.accel = getAccel();
  }

  public void updateAngle(double angle)
  {
    this.angle = angle;

    this.Fnorm = getFnorm();
    this.Ffric = getFriction();
    this.accel = getAccel();
  }

  public void move()
  {
    this.x += 0.01 * vel * Math.sin(angle);
    this.y += 0.01 * vel * Math.cos(angle);
  }

  public String toString()
  {
    return "Fg: " + Fgrav + ", Fapp: " + Fapp + ", Fnorm: " + Fnorm + ", Ffric: " + Ffric;
  }

}
