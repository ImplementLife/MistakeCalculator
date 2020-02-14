import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Loader {
    static SettingGUI settingGUI = new SettingGUI();
    private static JFrame frame = new JFrame("myErrCalc 1.0");
    private static MainGUI mainGUI;

    public static void main(String[] args) {
        //UIManager.setLookAndFeel(UIManager.);

        frame.setLayout(new BorderLayout());
        mainGUI = new MainGUI();
        ////////////////////////////////////

        JMenuBar menuBar = new JMenuBar();
        ////////////////////////////////////
        menuBar.add(menuFile());
        menuBar.add(menuOther());
        ////////////////////////////////////

        frame.setJMenuBar(menuBar);
        frame.add(mainGUI.getPanel());
        frame.revalidate();

        frame.setMinimumSize(new Dimension(265, 300));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Вынесено отдельно для контроля состояния
    private static JMenuItem save;

    //Формирование содержимого в выпадающем меню "Файл"
    private static JMenu menuFile() {
        JMenu file = new JMenu("Файл");

        save = file.add(new JMenuItem("Сохранить"));
        save.setEnabled(false);
        save.addActionListener(e -> {
            if(e.getSource() == save) {
                mainGUI.saver(save());
            }
        });

        JMenuItem open = new JMenuItem("Открыть");
        open.setEnabled(true);
        open.addActionListener(e -> {
            if(e.getSource() == open) {
                mainGUI.loader(open());
            }
        });

        JMenuItem exit = new JMenuItem("Выход");
        exit.addActionListener(e -> {
            if(e.getSource() == exit) {
                System.exit(0);
            }
        });

        ///////////////////////////////

        file.add(open);
        file.addSeparator();
        file.add(exit);

        return file;
    }

    //Формирование содержимого в выпадающем меню "Другое"
    private static JMenu menuOther() {
        JMenu other = new JMenu("Другое");

        InfoGUI01 infoGUI01 = new InfoGUI01();

        JMenuItem setting = new JMenuItem("Настройки");
        setting.addActionListener(e -> {
            if(e.getSource() == setting) {
                settingGUI.setVisible(true);
            }
        });

        JMenuItem info01 = new JMenuItem("О программе");
        info01.addActionListener(e -> {
            if (e.getSource() == info01) {
                infoGUI01.setVisible(true);
            }
        });

        ///////////////////////////////

        other.add(setting);
        other.addSeparator();
        other.add(info01);

        return other;
    }
    
    //Контроль состояния кнопки сохранить
    static void setSaveEnabled(boolean b) {
        save.setEnabled(b);
    }

    //Метод возвращает объект файла который нужно открыть
    private static File open() {
        //Открыть через проводник
        File file;
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        } else {
            file = null;
        }
        return file;
    }

    //Метод возвращает объект файла который нужно сохранить
    private static File save() {
        //Сохранить через проводник
        File file;
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        } else {
            file = null;
        }
        return file;
    }

}
