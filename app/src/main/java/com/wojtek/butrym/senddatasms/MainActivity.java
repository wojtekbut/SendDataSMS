package com.wojtek.butrym.senddatasms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    EditText numer;
    Button zadzwon, gdzie, kontakt;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        numer = (EditText) findViewById(R.id.numerTel);
        zadzwon = (Button) findViewById(R.id.zadzwon);
        gdzie = (Button) findViewById(R.id.gdzie);
        kontakt = (Button) findViewById(R.id.kontakt);
        zadzwon.setOnClickListener(this);
        gdzie.setOnClickListener(this);
        kontakt.setOnClickListener(this);

        Log.e("onCreate", "koniec");
    }

    @Override
    public void onClick(View view) {
        Log.e("onClick", "startuje");
        if (numer.getText().toString().equals("") && (view.getId() != R.id.kontakt)){
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
            case R.id.kontakt:
                Intent pickContactIntent = new Intent( Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI );
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContactIntent, 1);
            default:
                Log.e("switch","koniec");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String phoneNo = null;
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor.moveToFirst()) {
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                phoneNo = cursor.getString(phoneIndex);
            }

            cursor.close();
            numer.setText(phoneNo);
        }
    }
}
