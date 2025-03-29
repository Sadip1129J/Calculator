package calculator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MobileCalculator extends Application {

    private TextField display;
    private String currentNumber = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean startNewNumber = true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mobile Calculator");

        // Create the display
        display = new TextField();
        display.setEditable(false);
        display.setPrefHeight(50);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setFont(javafx.scene.text.Font.font("Arial", 24));
        display.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000;");

        // Create a grid for buttons
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setPadding(new Insets(10));

        // Button labels
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "DEL", "C",
            "", "", "=", "+" // Adding empty strings for spacing
        };

        // Create buttons and add them to the grid
        int row = 0;
        int col = 0;
        for (String label : buttonLabels) {
            if (!label.isEmpty()) { // Only add non-empty labels
                Button button = createButton(label);
                buttonGrid.add(button, col, row);
                col++;
                if (col > 3) {
                    col = 0;
                    row++;
                }
            }
        }

        // Create a vertical box to hold the display and button grid
        VBox root = new VBox(10, display, buttonGrid);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2E2E2E;"); // Set background color to light black

        // Create a new grid for the top buttons (C and DEL)
        GridPane topButtonGrid = new GridPane();
        topButtonGrid.setHgap(10);
        topButtonGrid.setVgap(10);
        topButtonGrid.setPadding(new Insets(10));
        topButtonGrid.add(createButton("C"), 0, 0);
        topButtonGrid.add(createButton("DEL"), 1, 0);

        // Create a new grid for the bottom buttons (numbers and operators)
        GridPane bottomButtonGrid = new GridPane();
        bottomButtonGrid.setHgap(10);
        bottomButtonGrid.setVgap(10);
        bottomButtonGrid.setPadding(new Insets(10));

        // Add number and operator buttons to the bottom grid
        row = 0;
        col = 0;
        for (String label : buttonLabels) {
            if (!label.isEmpty() && !label.equals("C") && !label.equals("DEL") && !label.equals("=")) {
                Button button = createButton(label);
                bottomButtonGrid.add(button, col, row);
                col++;
                if (col > 3) {
                    col = 0;
                    row++;
                }
            }
        }

        // Add the "=" button to the bottom right
        bottomButtonGrid.add(createButton("="), 3, row);

        // Create a vertical box to hold the display, top buttons, and bottom buttons
        VBox mainLayout = new VBox(10, display, topButtonGrid, bottomButtonGrid);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #2E2E2E;"); // Set background color to light black

        // Set the scene and show the stage
        Scene scene = new Scene(mainLayout, 300, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(70, 70);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");
        button.setOnAction(e -> handleButtonClick(text));
        return button;
    }

    private void handleButtonClick(String text) {
        switch (text) {
            case "C": // Clear All
                currentNumber = "";
                operator = "";
                firstOperand = 0;
                startNewNumber = true;
                display.setText("");
                break;
            case "DEL": // Delete last character
                if (!currentNumber.isEmpty()) {
                    currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
                    display.setText(currentNumber);
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (!currentNumber.isEmpty()) {
                    firstOperand = Double.parseDouble(currentNumber);
                    operator = text;
                    currentNumber = "";
                    startNewNumber = true;
                }
                break;
            case "=":
                if (!currentNumber.isEmpty() && operator != null) {
                    double secondOperand = Double.parseDouble(currentNumber);
                    double result = performOperation(firstOperand, secondOperand, operator);
                    display.setText(String.valueOf(result));
                    currentNumber = String.valueOf(result);
                    operator = null;
                    startNewNumber = true;
                }
                break;
            default: // Number or decimal point
                if (startNewNumber) {
                    currentNumber = text;
                    startNewNumber = false;
                } else {
                    currentNumber += text;
                }
                display.setText(currentNumber);
                break;
        }
    }

    private double performOperation(double first, double second, String operator) {
        switch (operator) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                if (second != 0) {
                    return first / second;
                } else {
                    display.setText(" Error");
                    return 0;
                }
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}