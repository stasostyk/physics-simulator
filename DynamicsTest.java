public class DynamicsTest
{
  public static void main(String[] args)
  {
    double mass = 10;
    DynamicObject obj = new DynamicObject(mass, 15, 0.5, 0.4, 30); //double m, double a, double muS, double muK, double Fapp
    // System.out.println(obj.getAccel());
    // while (true)
    // {
    //   obj.updateMass(mass+=0.1);
    //   System.out.println(obj);
    // }
  }
}
