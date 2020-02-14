import javax.swing.*;
import java.awt.*;

public class SettingGUI extends JFrame{
    private JPanel panel;
    private JSlider slider1;

    SettingGUI() {
        super("Setting");

        slider1.setMinimum(1);
        slider1.setMaximum(7);
        slider1.setValue(3);
        slider1.setMajorTickSpacing(1);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);

        setContentPane(panel);

        setMinimumSize(new Dimension(300, 150));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    int getValue() {
        return slider1.getValue();
    }

}
