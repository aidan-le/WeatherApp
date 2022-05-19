import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.AbstractButton;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Gui implements ActionListener {
    private Weather weather;
    private String zipCode;
    private boolean showCelsius;

    private JTextField zipCodeEntryField;
    private JLabel temperatureLabel;
    private JLabel conditionLabel;
    private JLabel conditionIcon;

    public Gui() {
        zipCode = "11217";
        showCelsius = false;

        zipCodeEntryField = new JTextField();
        temperatureLabel = new JLabel();
        conditionLabel = new JLabel();
        conditionIcon = new JLabel();

        weather = retrieveWeather();
    }

    public void setupGui() {
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Current Weather");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        titleLabel.setForeground(Color.blue);
        titlePanel.add(titleLabel);

        JPanel contentPanel = new JPanel();
        JLabel zipcodeHint = new JLabel("Enter Zip Code:");
        zipCodeEntryField = new JTextField(zipCode, 6);
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");
        JCheckBox showCelsius = new JCheckBox("Show Celsius");
        contentPanel.add(zipcodeHint);
        contentPanel.add(zipCodeEntryField);
        contentPanel.add(submitButton);
        contentPanel.add(clearButton);
        contentPanel.add(showCelsius);

        JPanel weatherPanel = new JPanel();
        temperatureLabel = new JLabel("Temperature: "+weather.getTemperatureF()+" F");
        conditionLabel = new JLabel("Condition: "+weather.getCurrentCondition());

        try {
            URL imageURL = new URL(weather.getConditionIcon());
            BufferedImage image = ImageIO.read(imageURL);
            conditionIcon = new JLabel(new ImageIcon(image));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        weatherPanel.add(temperatureLabel);
        weatherPanel.add(conditionLabel);
        weatherPanel.add(conditionIcon);

        // init
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(weatherPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        showCelsius.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void updateGui() {
        double temperature;
        String temperatureUnit;
        if (showCelsius) {
            temperature = weather.getTemperatureC();
            temperatureUnit = "C";
        } else {
            temperature = weather.getTemperatureF();
            temperatureUnit = "F";
        }

        temperatureLabel.setText("Temperature: "+temperature+" "+temperatureUnit);
        conditionLabel.setText("Condition: "+weather.getCurrentCondition());


        try {
            URL imageURL = new URL(weather.getConditionIcon());
            BufferedImage image = ImageIO.read(imageURL);
            conditionIcon.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Weather retrieveWeather() {
        return Networking.getCurrentWeather(zipCode);
    }

    public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) (e.getSource());
        String text = button.getText();

        if (text.equals("Submit")) {
            zipCode = zipCodeEntryField.getText();
            weather = retrieveWeather();
        } else if (text.equals("Clear")) {
            zipCodeEntryField.setText("");
        } else if (text.equals("Show Celsius")) {
            showCelsius = button.isSelected();
        }
        updateGui();
    }
}