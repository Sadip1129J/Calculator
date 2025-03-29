package calculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
        display.setPrefHeight(60);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setFont(javafx.scene.text.Font.font("Arial", 24));
        display.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000;");
        display.relocate(10, 10);
        display.setPrefWidth(280);

        // Create buttons
        Button btnClear = createButton("C");
        Button btnDelete = createButton("DEL");
        
        Button btn7 = createButton("7");
        Button btn8 = createButton("8");
        Button btn9 = createButton("9");
        Button btnDivide = createButton("/");
        
        Button btn4 = createButton("4");
        Button btn5 = createButton("5");
        Button btn6 = createButton("6");
        Button btnMultiply = createButton("*");
        
        Button btn1 = createButton("1");
        Button btn2 = createButton("2");
        Button btn3 = createButton("3");
        Button btnSubtract = createButton("-");
        
        Button btn0 = createButton("0");
        Button btnDecimal = createButton(".");
        Button btnEquals = createButton("=");
        Button btnAdd = createButton("+");

        // Position buttons using relocate()
        // Clear and Delete buttons below textbox
        btnClear.relocate(10, 80);
        btnDelete.relocate(90, 80);
        
        // Number and operator buttons
        btn7.relocate(10, 160);
        btn8.relocate(90, 160);
        btn9.relocate(170, 160);
        btnDivide.relocate(250, 160);
        
        btn4.relocate(10, 240);
        btn5.relocate(90, 240);
        btn6.relocate(170, 240);
        btnMultiply.relocate(250, 240);
        
        btn1.relocate(10, 320);
        btn2.relocate(90, 320);
        btn3.relocate(170, 320);
        btnSubtract.relocate(250, 320);
        
        btn0.relocate(10, 400);
        btnDecimal.relocate(90, 400);
        btnEquals.relocate(170, 400);
        btnAdd.relocate(250, 400);

        // Create the main pane
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #2E2E2E;");
        pane.setPrefSize(330, 500);
        
        // Add all components to the pane
        pane.getChildren().add(display);
        pane.getChildren().addAll(
            btnClear, btnDelete,
            btn7, btn8, btn9, btnDivide,
            btn4, btn5, btn6, btnMultiply,
            btn1, btn2, btn3, btnSubtract,
            btn0, btnDecimal, btnEquals, btnAdd
        );

        // Set the scene and show the stage
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(70, 70);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; " +
                       "-fx-border-radius: 10; -fx-background-radius: 10;");
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
