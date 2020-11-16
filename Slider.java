import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Slider
{
  private JSlider slider;
  public int value;
  public Slider(int min, int max, int init)
  {
    slider = new JSlider(JSlider.VERTICAL, min, max, init);
    slider.setFocusable(false);
    slider.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent e)
      {
        value = slider.getValue();
      }
    });
  }
  public JSlider getSlider()
  {
    return slider;
  }
}
