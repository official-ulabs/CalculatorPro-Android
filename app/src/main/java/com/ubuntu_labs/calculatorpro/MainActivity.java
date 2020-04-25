package com.ubuntu_labs.calculatorpro;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView holdText, calculateText;

    String previousAnswerString = "";
    String operator = "";
    double x1 = 0, x2 = 0;

    RelativeLayout clearBtn, negativeBtn, percentageBtn, dividerBtn;
    RelativeLayout nineBtn, eightBtn, sevenBtn, multiplyBtn;
    RelativeLayout sixBtn, fiveBtn, fourBtn, subtractBtn;
    RelativeLayout threeBtn, twoBtn, oneBtn, addBtn;
    RelativeLayout calculateBtn, answerBtn, dotBtn, zeroBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        holdText = findViewById(R.id.holdText); // Used to hold a text when an operator has been selected
        calculateText = findViewById(R.id.answerText); // Used as input for the entered text

        // buttons for all inputs
        clearBtn = findViewById(R.id.acBtn);
        negativeBtn = findViewById(R.id.negativeBtn);
        percentageBtn = findViewById(R.id.percentageBtn);
        nineBtn = findViewById(R.id.nineBtn);
        dividerBtn = findViewById(R.id.dividerBtn);
        eightBtn = findViewById(R.id.eightBtn);
        sevenBtn = findViewById(R.id.sevenBtn);
        multiplyBtn = findViewById(R.id.multiplyBtn);
        sixBtn = findViewById(R.id.sixBtn);
        fiveBtn = findViewById(R.id.fiveBtn);
        fourBtn = findViewById(R.id.fourBtn);
        subtractBtn = findViewById(R.id.subtractBtn);
        threeBtn = findViewById(R.id.threeBtn);
        twoBtn = findViewById(R.id.twoBtn);
        oneBtn = findViewById(R.id.oneBtn);
        addBtn = findViewById(R.id.plusBtn);
        calculateBtn = findViewById(R.id.calculateBtn);
        answerBtn = findViewById(R.id.ansBtn);
        dotBtn = findViewById(R.id.dotBtn);
        zeroBtn = findViewById(R.id.zeroBtn);

        // Set onclick handler for all the buttons

        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("9");
            }
        });
        eightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("8");
            }
        });
        sevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("7");
            }
        });
        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("6");
            }
        });
        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("5");
            }
        });
        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("4");
            }
        });
        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("3");
            }
        });
        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("2");
            }
        });
        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("1");
            }
        });
        zeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText("0");
            }
        });

        // Clear button just clears the holding text and calculate text too
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdText.setText("0");
                calculateText.setText("0");
            }
        });


        // Answer button sets the saved answer text to the calculator text
        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateText.setText(previousAnswerString);
            }
        });

        // These four buttons sets the operator
        dividerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("/");
            }
        });

        multiplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("*");
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("+");
            }
        });

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOperator("-");
            }
        });

        // This negative button just negates or makes positive a negative number
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchNumberState();
            }
        });

        // The percentage button converts whatever text in the calculating area to percentage decimals
        percentageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateText.setText(
                        String.valueOf(
                                Double.parseDouble(calculateText.getText().toString())
                                        /
                                        100.0)
                );
            }
        });

        dotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalculateText(".");
            }
        });

        // Performs calculation
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runCalculator();
            }
        });

    }

    private void setCalculateText(String text) {
        if(calculateText.getText().toString().equals("0") && text.equals(".")) { // Makes sure if the first digit is zero, it adds . after the zero and not replace it immediately
            calculateText.setText("0.");
        } else if(calculateText.getText().toString().equals("0")) {
            calculateText.setText(text); // Set the text to whatever is passed
        } else {
            calculateText.setText(String.format("%s%s", calculateText.getText().toString(), text)); // Adds to an already entered text
        }
    }

    private void switchNumberState() {

        String calculateTextInput = calculateText.getText().toString();

        if(calculateTextInput.charAt(0) == '-') {
            calculateText.setText(calculateTextInput.substring(1));
        } else {
            calculateText.setText(String.format("-%s", calculateTextInput));
        }
    }

    private void runCalculator() {

        if(!operator.isEmpty()) { // Make sure an operator has been selected
            x2 = Double.parseDouble(calculateText.getText().toString()); // Get the second value to perform the calculation on

            switch (operator.charAt(0)) { // Check for which operator and apply the appropriate math operation
                case '+': {
                    calculateText.setText(
                            placeCommaInEveryThousand(
                                    String.format(Locale.getDefault(),"%.2f", x1 + x2)
                            )
                    );
                    break;
                }
                case '-': {
                    calculateText.setText(
                            placeCommaInEveryThousand(
                                    String.format(Locale.getDefault(),"%.2f", x1 - x2)
                            )
                    );
                    break;
                }
                case '/': {
                    calculateText.setText(
                            placeCommaInEveryThousand(
                                    String.format(Locale.getDefault(),"%.2f", x1 / x2)
                            )
                    );
                    break;
                }
                case '*': {
                    calculateText.setText(
                            placeCommaInEveryThousand(
                                    String.format(Locale.getDefault(),"%.2f", x1 * x2)
                            )
                    );
                    break;
                }
                default: Snackbar.make(zeroBtn, "Invalid Operator", BaseTransientBottomBar.LENGTH_LONG).show();
            }

            // Remember the A button, whatever answer is provided is placed in the A button. So when it is clicked, the previous answer
            // is displayed. It helps makes calculation faster
            previousAnswerString = calculateText.getText().toString();

            holdText.setText("0"); // Reset the calculator
            operator = "";
        } else {
            previousAnswerString = calculateText.getText().toString();
        }
    }

    private void setOperator(String operatorSign) {

        if(Double.parseDouble(holdText.getText().toString()) != 0 && Double.parseDouble(calculateText.getText().toString()) != 0
                && !operator.isEmpty()) {
            runCalculator();
        } // This If statement makes sure that if we press 8 + 8 and another + 8. It doesn't clear the calculate area but instead
        // run calculation on the first 8 + 8 = 16 and then takes the answer and adds the new 8 to it.
        // So 8 + 8 = 16 + 8 = 24

        holdText.setText(calculateText.getText().toString()); // Add the first text to the holding Text for later calculations

        x1 = Double.parseDouble(calculateText.getText().toString()); // Set the first calculating input

        operator = operatorSign; // Set the operator Sign
        calculateText.setText("0"); // Reset the calculating area for the new input to work on.

    }

    // This function places a comma where appropriate for every thousand-th
    // So 23334 becomes 23,334. Just a nice touch

    private String placeCommaInEveryThousand(String str) {
        try {
            if (str.length() < 4) {
                return str;
            }

            if(Integer.parseInt(str) > 999) {
                return placeCommaInEveryThousand(str.substring(0, str.length() - 3)) + "," + str.substring(str.length() - 3);
            } else {
                return str;
            }
        } catch (Exception e) {
            return str;
        }
    }

    // LETS run the CODE ........
    // Code will be pushed to Github.
    // Like, Share and Subscribe. Thank you. Stay Safe Guys .. Peace Out

}
