import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CurrencyAndTemperatureConverter {
    private static Map<String, Map<String, Double>> exchangeRates = new HashMap<>();
    private static JFrame frame;
    private static JPanel panel;
    private static JTextField textField;
    private static JComboBox<String> fromCurrencyComboBox;
    private static JComboBox<String> toCurrencyComboBox;

    static {
        Map<String, Double> usdRates = new HashMap<>();
        usdRates.put("Dólares", 1.0);
        usdRates.put("Pesos Mexicanos", 16.0);
        usdRates.put("Euros", 0.85);
        usdRates.put("Rupia India", 74.0);
        usdRates.put("Yen", 110.0);
        usdRates.put("Yuan", 6.5);
        usdRates.put("Peso Argentino", 95.0);
        exchangeRates.put("Dólares", usdRates);
    }

    public static void main(String[] args) {
        frame = new JFrame("Conversor de Monedas y Temperatura");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(192, 192, 192));
        frame.add(panel);

        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JButton themeButton = new JButton("Cambiar Tema");
        themeButton.addActionListener(e -> toggleTheme());
        panel.add(themeButton);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        panel.add(progressBar);

        JLabel label = new JLabel("Cantidad:");
        textField = new JTextField(10);
        panel.add(label);
        panel.add(textField);

        JLabel fromCurrencyLabel = new JLabel("Convertir de:");
        fromCurrencyComboBox = new JComboBox<>(new String[]{"Dólares", "Euros", "Pesos Mexicanos", "Rupia India", "Yen", "Yuan", "Peso Argentino"});
        panel.add(fromCurrencyLabel);
        panel.add(fromCurrencyComboBox);

        JLabel toCurrencyLabel = new JLabel("Convertir a:");
        toCurrencyComboBox = new JComboBox<>(new String[]{"Dólares", "Euros", "Pesos Mexicanos", "Rupia India", "Yen", "Yuan", "Peso Argentino"});
        panel.add(toCurrencyLabel);
        panel.add(toCurrencyComboBox);

        JButton convertCurrencyButton = new JButton("Convertir Moneda");
        panel.add(convertCurrencyButton);

        convertCurrencyButton.addActionListener(e -> {
            String amountText = textField.getText();
            String fromCurrency = (String) fromCurrencyComboBox.getSelectedItem();
            String toCurrency = (String) toCurrencyComboBox.getSelectedItem();
            try {
                double amount = Double.parseDouble(amountText);
                double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
                JOptionPane.showMessageDialog(frame, amount + " " + fromCurrency + " son " + convertedAmount + " " + toCurrency);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido.");
            }
        });

        JButton convertTemperatureButton = new JButton("Convertir a Fahrenheit");
        panel.add(convertTemperatureButton);

        convertTemperatureButton.addActionListener(e -> {
            String text = textField.getText();
            try {
                double celsius = Double.parseDouble(text);
                double fahrenheit = celsius * 9/5 + 32;
                JOptionPane.showMessageDialog(frame, celsius + " grados Celsius son " + fahrenheit + " grados Fahrenheit.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido.");
            }
        });

        frame.setVisible(true);
    }

    private static double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        Map<String, Double> fromRates = exchangeRates.get(fromCurrency);
        if (fromRates != null) {
            Double toRate = fromRates.get(toCurrency);
            if (toRate != null) {
                return amount * toRate;
            }
        }
        return 0;  // Devuelve 0 si no se encuentra la tasa de cambio
    }

    private static void toggleTheme() {
        Color lightColor = new Color(255, 255, 255);
        Color darkColor = new Color(0, 0, 0);
        Color currentColor = panel.getBackground();

        if (currentColor.equals(lightColor)) {
            panel.setBackground(darkColor);
            frame.getContentPane().setBackground(darkColor);
        } else {
            panel.setBackground(lightColor);
            frame.getContentPane().setBackground(lightColor);
        }
    }
}

