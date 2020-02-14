import javax.swing.*;
import java.awt.*;

public class InfoGUI01 extends JFrame {

    InfoGUI01() {
        add(new Label("Разработано студентом НТУ \"ДП\""), BorderLayout.NORTH);
        add(new Label("Группа: 141-19ск-4"), BorderLayout.WEST);
        add(new Label("Роенко Матвей Антонович"), BorderLayout.CENTER);
        add(new Label("2019 год."), BorderLayout.SOUTH);

        setTitle("О Программе");
        setSize(300, 100);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
