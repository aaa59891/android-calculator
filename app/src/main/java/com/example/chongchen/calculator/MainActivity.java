package com.example.chongchen.calculator;

import android.content.Context;
import android.content.SharedPreferences;
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

    private Button btnDot;

    private Button btnPMSwitch;

    private ArrayList<Button> numberBtns;
    private ArrayList<Button> operationBtns;

    private static final String TAG = "MainActivity";
    private static final String STATE_RESET = "reset";
    private static final String STATE_TEMP = "temp";
    private static final String STATE_OPERATION = "operation";
    private static boolean reset = false;
    private double temp = 0.0;
    private String operation = "";

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

        View.OnClickListener operationListener = v -> {
            Button btn = (Button) v;
            String currentOperation = btn.getText().toString();
            if(reset){
                this.operation = currentOperation;
                return;
            }
            String currentNum = this.txtResult.getText().toString();
            double current = 0;
            if (currentNum.length() > 0) {
                current = Double.parseDouble(currentNum);
            }

            if(this.temp != 0 || current != 0){
                switch (operation) {
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
            this.operation = currentOperation;
            reset = true;
        };

        for (Button btn : this.numberBtns) {
            btn.setOnClickListener(numListener);
        }

        for (Button btn : this.operationBtns) {
            btn.setOnClickListener(operationListener);
        }

        this.btnC.setOnClickListener(
                v -> {
                    this.temp = 0.0;
                    this.operation = "";
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

        this.btnPMSwitch.setOnClickListener(
                v -> {
                    String resultStr = this.txtResult.getText().toString();

                    if(reset){
                        this.temp *= -1;
                    }

                    if(resultStr.contains("-")){
                        resultStr = resultStr.substring(1);
                    }else{
                        resultStr = "-" + resultStr;
                    }
                    this.txtResult.setText(resultStr);
                }
        );

    }

    private void initObjects() {
        this.txtResult = findViewById(R.id.result);
        this.txtResult.setFocusable(false);
        this.txtResult.setFocusableInTouchMode(false);
        this.btnC = findViewById(R.id.btnC);
        this.btnPMSwitch = findViewById(R.id.btnPMSwitch);

        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);

        this.btnDot = findViewById(R.id.btnDot);
        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnEqual = findViewById(R.id.btnEqual);
        Button btnDivide = findViewById(R.id.btnDivide);

        this.numberBtns = new ArrayList<>(Arrays.asList(
                btn0,
                btn1,
                btn2,
                btn3,
                btn4,
                btn5,
                btn6,
                btn7,
                btn8,
                btn9
        ));

        this.operationBtns = new ArrayList<>(Arrays.asList(
                btnDivide,
                btnMultiply,
                btnMinus,
                btnEqual,
                btnPlus
        ));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        sp.edit().putString(STATE_OPERATION, this.operation)
                .putLong(STATE_TEMP, Double.doubleToRawLongBits(this.temp))
                .putBoolean(STATE_RESET, reset).apply();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        this.operation = sp.getString(STATE_OPERATION, "");
        long a = sp.getLong(STATE_TEMP, Double.doubleToLongBits(0));
        this.temp = Double.longBitsToDouble(a);
        reset = sp.getBoolean(STATE_RESET, false);
    }
}
