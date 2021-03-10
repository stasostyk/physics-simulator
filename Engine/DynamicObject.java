package Engine;

public class DynamicObject
{
  public static double g = 9.81;
  public double mass, angle, muS, muK; // mass, angle, static coefficient of friction, kinetic coefficient of friction
  public double Fapp, Fnorm, Fgrav, Ffric; // applied, normal, gravitational, and friction force
  public double vel, accel;
  public int x, y = 0;

  public boolean isDragging = false;

  public DynamicObject(double m, double a, double muS, double muK, double Fapp)
  {
    this.mass = m;
    this.angle = Math.toRadians(a);
    this.muS = muS;
    this.muK = muK;
    this.Fapp = Fapp;
    this.vel = 0;

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
    if (Fapp + Fgrav * Math.sin(angle) >= -Fnorm * muS && Fnorm * muS >= Fapp + Fgrav * Math.sin(angle)) // is not moving
    {
      return -Fgrav*Math.sin(angle) - Fapp;
    }

    int direction = -1; // +1 or -1
    if (Fapp < 0)
      if (accel < 0 && (Fapp + Fgrav*Math.sin(angle)) < 0)
        direction = 1;

    return muK * Fnorm * direction;
  }

  public double getAccel()
  {
    // if (Fgrav*Math.sin(angle) + Fapp <= muS * Fnorm) return 0;
    return (Fapp + Fgrav*Math.sin(angle) + Ffric) / mass;   // Fnet = m * a
  }

  public void update()
  {
    this.Ffric = getFriction();
    this.vel += accel;
    move();
  }

  public void updateFapp(double newFapp)
  {
    this.Fapp = newFapp;
    this.Ffric = getFriction();
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
    this.x += 0.1 * vel * Math.sin(angle);
    this.y += 0.1 * vel * Math.cos(angle);
  }

  public String toString()
  {
    return "Fg: " + Fgrav + ", Fapp: " + Fapp + ", Fnorm: " + Fnorm + ", Ffric: " + Ffric + ", Accel = " + accel;
  }

}
