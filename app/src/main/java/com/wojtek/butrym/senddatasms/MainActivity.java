package com.wojtek.butrym.senddatasms;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    EditText numer;
    Button zadzwon, gdzie;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        numer = (EditText) findViewById(R.id.numerTel);
        zadzwon = (Button) findViewById(R.id.zadzwon);
        gdzie = (Button) findViewById(R.id.gdzie);
        zadzwon.setOnClickListener(this);
        gdzie.setOnClickListener(this);

        Log.e("onCreate", "koniec");
    }

    @Override
    public void onClick(View view) {
        Log.e("onClick", "startuje");
        if (numer.getText().toString().equals("")){
            Log.e("if","Pusty");
            Toast toast = Toast.makeText(context,"Podaj numer",Toast.LENGTH_SHORT);
            toast.show();
            return;
        } else {
            Log.e("if","nie pusty");
        }
        SmsManager smsManager = SmsManager.getDefault();
        short port = 6543;
        String phoneNumber = numer.getText().toString();

        switch (view.getId()) {
            case R.id.zadzwon:
                Log.e("switch","zadzwon");
                byte[] smsBody1 = "Zadzwon".getBytes();
                smsManager.sendDataMessage(phoneNumber, null, port, smsBody1, null, null);
                break;
            case R.id.gdzie:
                Log.e("switch","gdzie");
                byte[] smsBody2 = "Gdzie jestes?".getBytes();
                smsManager.sendDataMessage(phoneNumber, null, port, smsBody2, null, null);
                break;
            default:
                Log.e("switch","koniec");

        }


    }
}
