package denis_lebedev.pairtrading.gui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import denis_lebedev.pairtrading.R;
import denis_lebedev.pairtrading.logic.DateUtils;
import denis_lebedev.pairtrading.logic.MyApplication;
import denis_lebedev.pairtrading.logic.SessionItem;

public class MainActivity extends AppCompatActivity {

    private MyApplication myApp = MyApplication.getInstance();

    private SessionItem sessionItem = new SessionItem();

    private EditText balanceTxt;
    private EditText riskTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceTxt = (EditText)findViewById(R.id.balanceTxt);
        riskTxt = (EditText)findViewById(R.id.riskTxt);
    }

    public void startButton_OnClick(View view){

    }

    public void startSelectSymbols(View view){
        Intent intent = new Intent(this, ChooseStockSymbols.class);
        startActivity(intent);
    }


    public void setFirstDate(View view){
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this, firstDateListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener firstDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            sessionItem.firstDate = DateUtils.getDateFromDatePicker(datePicker);
        }
    };

    public void setLastDate(View view){
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this, lastDateListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener lastDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            sessionItem.lastDate = DateUtils.getDateFromDatePicker(datePicker);
        }
    };

    public void OK_ButtonClick(View view){

        sessionItem.balance = Double.parseDouble(balanceTxt.getText().toString());
        sessionItem.risk = Double.parseDouble(riskTxt.getText().toString());

        System.out.println(sessionItem.firstDate);
        System.out.println(sessionItem.lastDate);
        System.out.println(sessionItem.balance);
        System.out.println(sessionItem.risk);
    }
}
