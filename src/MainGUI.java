import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class MainGUI extends JPanel{

    private JPanel panel;
    private JPanel panel1;
    private JScrollPane panel2;
    private JPanel panel3;
    private JPanel pan;
    private JScrollPane panel4;

    private JComboBox comboBox1;

    private JTextArea textArea1;

    private JTextField textField01;
    private JTextField textField02;
    private JTextField textField03;
    private JTextField textField04;
    private JTextField textField05;
    private JTextField textField06;
    private JTextField textField07;
    private JTextField textField08;
    private JTextField textField09;
    private JTextField textField10;

    private ArrayList<JTextField> textFields;

    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;

    private JButton button;
    private JButton button2;

    private ArrayList<Double> digits = new ArrayList<>();
    private ArrayList<Double> digitsErr = new ArrayList<>();
    private ArrayList<Double> digitsErrSqr = new ArrayList<>();
    private double mX;
    private double middleS;
    private double errX;

    private StringBuilder str;

    private JSONObject object;

    //Таблица значений коэффициента Стьюдента
    private double[][] kSt = {{3.1, 1.9, 1.6, 1.5, 1.5, 1.4, 1.4, 1.4, 1.4},
                              {6.3, 2.9, 2.4, 2.1, 2.0, 1.9, 1.9, 1.9, 1.8},
                              {12.7, 4.3, 3.2, 2.8, 2.6, 2.4, 2.4, 2.3, 2.3}
                             };

    MainGUI() {
        comboBox1.addItem("80%");
        comboBox1.addItem("90%");
        comboBox1.addItem("95%");

        textFields = new ArrayList<>();

        //Забираем ссылки на поля ввода в отдельный массив
        textFields.add(textField01);
        textFields.add(textField02);
        textFields.add(textField03);
        textFields.add(textField04);
        textFields.add(textField05);
        textFields.add(textField06);
        textFields.add(textField07);
        textFields.add(textField08);
        textFields.add(textField09);
        textFields.add(textField10);

        textArea1.setMinimumSize(new Dimension(200, 100));

        //Кнопка расчитать
        button.addActionListener(e -> {
            if(e.getSource() == button){
                str = new StringBuilder();
                digits.clear();
                digitsErr.clear();
                digitsErrSqr.clear();

                mX = 0;
                middleS = 0;
                errX = 0;

                for (JTextField textField : textFields) addValueToDigits(textField.getText());

                int countValues = digits.size();

                double sumDigits = 0;
                for(double num : digits) sumDigits += num;

                //Среднеквадратическая ошибка среднего арифметического
                mX = sumDigits / (countValues);

                for(double num : digits) digitsErr.add(mX - num); //Ошибка каждого
                for(double num : digitsErr) digitsErrSqr.add(num * num); //Возведение в квадрат

                double sumDigitsErrSqr = 0;
                for(double num : digitsErrSqr) sumDigitsErrSqr += num; //Сумма квадратов

                //Среднеквадратическая ошибка среднего арифметического
                middleS = Math.sqrt(sumDigitsErrSqr / (countValues * (countValues - 1)));

                //Поиск нужного коэффициента
                double kF = -1000000;
                if (Objects.equals(comboBox1.getSelectedItem(), "80%")) kF = kSt[0][(int)countValues-2];
                else if (Objects.equals(comboBox1.getSelectedItem(), "90%")) kF = kSt[1][(int)countValues-2];
                else if (Objects.equals(comboBox1.getSelectedItem(), "95%")) kF = kSt[2][(int)countValues-2];

                errX = middleS * kF;

                double E = ((errX / mX) * 100);

                //Подготовка к выводу результата
                str.append("<X> = ").append(getValueAndIndex(mX, Loader.settingGUI.getValue())).append(";\n");
                for (int i = 0; i < countValues; i++) {
                    if (countValues < 10) str.append("x_0").append(i + 1).append(" = ").append(getValueAndIndex(digits.get(i), Loader.settingGUI.getValue())).append(";\n");
                    else str.append("x_").append(i + 1).append(" = ").append(getValueAndIndex(digits.get(i), Loader.settingGUI.getValue())).append(";\n");
                }
                for (int i = 0; i < countValues; i++) {
                    if (countValues < 10) str.append("Δx_0").append(i + 1).append(" = ").append(getValueAndIndex(digitsErr.get(i), Loader.settingGUI.getValue())).append(";\n");
                    else str.append("Δx_").append(i + 1).append(" = ").append(getValueAndIndex(digitsErr.get(i), Loader.settingGUI.getValue())).append(";\n");
                }
                for (int i = 0; i < countValues; i++) {
                    if (countValues < 10) str.append("Δx_0").append(i + 1).append("^2 = ").append(getValueAndIndex(digitsErrSqr.get(i), Loader.settingGUI.getValue())).append(";\n");
                    else str.append("Δx_").append(i + 1).append("^2 = ").append(getValueAndIndex(digitsErrSqr.get(i), Loader.settingGUI.getValue())).append(";\n");
                }
                str.append("S_<X> = ").append(getValueAndIndex(middleS, Loader.settingGUI.getValue())).append(";\n");
                str.append("Коэффициент Стьюдента: kSt = ").append(kF).append(";\n");
                str.append("ΔX = ").append(getValueAndIndex(errX, Loader.settingGUI.getValue())).append(";\n");
                str.append("Относительная ошибка: E = ").append(getValueAndIndex(E, Loader.settingGUI.getValue())).append("%;\n");

                //Вывод результата
                textArea1.setText(str.toString());
            }
        });

        //Кнопка очистить
        button2.addActionListener(e -> {
            if(e.getSource() == button2){
                for (JTextField textField : textFields) textField.setText("");
                textArea1.setText("");
            }
        });

        new Updater();
    }

    //Метод для добавления в обработку значений из полей ввода
    private void addValueToDigits(String str) {
        if (str.length() >= 1) digits.add(getValue(str));
    }

    //Метод "фильтр" для отсечения лишнего в полях ввода
    private double getValue(String str) {
        char[] chars = str.toCharArray();
        StringBuilder str2 = new StringBuilder();
        for(char ch : chars) {
            if(Character.isDigit(ch) || ch == '-' || ch == '.' || ch == 'E') {
                str2.append(ch);
            }
        }
        return Double.valueOf(str2.toString());
    }

    //Метод для округления значения до определённого знака после запятой
    private static double getValueAndIndex(double d, int index) {
        double indexD = 1;
        for (int i = 0; i < index; i++) indexD *= 10;

        int i = (int)(d * indexD);
        return i / indexD;
    }

    //Метод для передачи визуальной оболочки в главный Фрейм
    JPanel getPanel() {
        return panel;
    }

    //Сохранение результата в JSON файл
    void saver(File file) {
        try {
            String path = file.getPath();
            String fileExpansion = ".json";
            String lastPath = "";

            try{
                int i = path.lastIndexOf('.');
                if (!path.substring(i).equals(fileExpansion)) {
                    String newPath = path.substring(0, i);
                    lastPath = newPath + fileExpansion;
                }
            } catch (StringIndexOutOfBoundsException e) { //Вылет при не правильном вводе имени файла (в lastIndexOf)
                lastPath = path + fileExpansion;
            }

            object = new JSONObject();
            JSONArray values  = new JSONArray();
            values.addAll(digits);
            object.put("coefficient", comboBox1.getSelectedItem());
            object.put("values", digits);
            object.put("result", str.toString());

            FileWriter fileWriter = new FileWriter(new File(lastPath));
            fileWriter.write(object.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Загружаемся из JSON файла
    void loader(File file) {
        JSONObject loadFile = null;

        try {
            loadFile = (JSONObject) new JSONParser().parse(new FileReader(file));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        comboBox1.setSelectedItem(loadFile.get("coefficient"));

        ArrayList list = (ArrayList) loadFile.get("values");

        for (int i = 0; i < list.size(); i++) textFields.get(i).setText(list.get(i).toString());

        textArea1.setText((String) loadFile.get("result"));
    }

    //Класс "костыль" вместо "ActionEvent || ActionListener" на полях ввода
    class Updater implements Runnable{
        Thread thread;

        Updater() {
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            while (true) {
                update();
            }
        }

        private void update() {
            try {
                thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte i = 0;

            for (JTextField textField : textFields) if (textField.getText().length() >= 1) i++;

            if (i <= 1) {
                button.setEnabled(false);
                Loader.setSaveEnabled(false);
            } else {
                if (textArea1.getText().length() >= 1) Loader.setSaveEnabled(true);
                button.setEnabled(true);
            }
        }
    }
}