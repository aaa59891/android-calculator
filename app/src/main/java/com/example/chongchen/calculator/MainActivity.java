package com.example.chongchen.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView txtResult;

    private Button btnC;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn0;

    private Button btnDot;

    private Button btnPlus;
    private Button btnMinus;
    private Button btnDivide;
    private Button btnMultiply;
    private Button btnEqual;

    private ArrayList<Button> numberBtns;
    private ArrayList<Button> operatorBtns;

    private static final String TAG = "MainActivity";
    private static boolean reset = false;
    private double temp = 0.0;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();

        View.OnClickListener numListener = v -> {
            Button btn = (Button) v;
            String str = btn.getText().toString();
            if (reset) {
                txtResult.setText(str);
                reset = !reset;
                return;
            }
            txtResult.append(str);
        };

        View.OnClickListener operatorListener = v -> {
            Button btn = (Button) v;
            String currentOperator = btn.getText().toString();
            if(reset){
                this.operator = currentOperator;
                return;
            }
            String currentStr = this.txtResult.getText().toString();
            double current = 0;
            if (currentStr.length() > 0) {
                current = Double.parseDouble(currentStr);
            }

            if(this.temp != 0 || current != 0){
                switch (operator) {
                    case "*":
                        this.temp *= current;
                        break;
                    case "/":
                        this.temp /= current;
                        break;
                    case "-":
                        this.temp -= current;
                        break;
                    case "+":
                        this.temp += current;
                        break;
                    default:
                        this.temp = current;
                }
            }
            String newRes;
            if(this.temp % 1 == 0){
                newRes = String.valueOf((int)this.temp);
            }else{
                newRes = String.valueOf(this.temp);
            }
            this.txtResult.setText(newRes);
            this.operator = currentOperator;
            reset = true;
        };

        for (Button btn : this.numberBtns) {
            btn.setOnClickListener(numListener);
        }

        for (Button btn : this.operatorBtns) {
            btn.setOnClickListener(operatorListener);
        }

        this.btnC.setOnClickListener(
                v -> {
                    this.temp = 0.0;
                    this.operator = "";
                    txtResult.setText("");
                }
        );

        this.btnDot.setOnClickListener(
                v -> {
                    String resultText = txtResult.getText().toString();
                    if (!resultText.contains(".") && resultText.length() > 0) {
                        txtResult.append(".");
                    }
                }
        );

    }

    private void initObjects() {
        this.txtResult = findViewById(R.id.result);
        this.txtResult.setFocusable(false);
        this.txtResult.setFocusableInTouchMode(false);
        this.btnC = findViewById(R.id.btnC);
        this.btn0 = findViewById(R.id.btn0);
        this.btn1 = findViewById(R.id.btn1);
        this.btn2 = findViewById(R.id.btn2);
        this.btn3 = findViewById(R.id.btn3);
        this.btn4 = findViewById(R.id.btn4);
        this.btn5 = findViewById(R.id.btn5);
        this.btn6 = findViewById(R.id.btn6);
        this.btn7 = findViewById(R.id.btn7);
        this.btn8 = findViewById(R.id.btn8);
        this.btn9 = findViewById(R.id.btn9);

        this.btnDot = findViewById(R.id.btnDot);
        this.btnPlus = findViewById(R.id.btnPlus);
        this.btnMinus = findViewById(R.id.btnMinus);
        this.btnMultiply = findViewById(R.id.btnMultiply);
        this.btnEqual = findViewById(R.id.btnEqual);
        this.btnDivide = findViewById(R.id.btnDivide);

        this.numberBtns = new ArrayList<>(Arrays.asList(
                this.btn0,
                this.btn1,
                this.btn2,
                this.btn3,
                this.btn4,
                this.btn5,
                this.btn6,
                this.btn7,
                this.btn8,
                this.btn9
        ));

        this.operatorBtns = new ArrayList<>(Arrays.asList(
                this.btnDivide,
                this.btnMultiply,
                this.btnMinus,
                this.btnEqual,
                this.btnPlus
        ));
    }

}
