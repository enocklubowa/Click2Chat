package com.techleadafrica.click2chat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://wa.me/";
    private TextInputEditText message_field, phone_field;
    private CountryCodePicker cpp;
    private String country_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //comment this line if you need to show Title.
        setContentView(R.layout.activity_main);
        initComponent();
        sendMessage();
    }

    private void initComponent(){
        phone_field = findViewById(R.id.phone_number);
        message_field = findViewById(R.id.message);
        cpp = findViewById(R.id.ccp);

        cpp.setOnCountryChangeListener(
                new CountryCodePicker.OnCountryChangeListener() {
                    @Override
                    public void onCountrySelected() {
                        country_code = cpp.getSelectedCountryCode();
                    }
                }
        );
        //message_field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    private void sendMessage(){
        ((AppCompatButton) findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((FloatingActionButton) findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = message_field.getText().toString();
                if(phone_field.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Phone field can not be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    String phone = country_code+phone_field.getText().toString();
                    String encoded_message = "";
                    try {
                        encoded_message = URLEncoder.encode(message,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        Toast.makeText(MainActivity.this, "We're sorry\nMessage format can't be sent", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    String url = BASE_URL+phone+"/?text="+encoded_message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    finish();
                }

            }
        });

    }
}