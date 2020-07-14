package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private DatabaseReference database_Led_1 = databaseReference.child("LED_STATUS");
    private DatabaseReference database_Led_2 = databaseReference.child("LED_STATUS_1");
    private DatabaseReference database_On_Off = databaseReference.child("SERVO");

    private TextView txtMQ2;
    private Switch Switch_Led_1,Switch_led_2;
    private Button btn_on, btn_off;
    DatabaseReference Database_MQ2;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch_Led_1 =(Switch)findViewById(R.id.switch_led1);
        Switch_led_2 =(Switch)findViewById(R.id.swit_led_2);
        txtMQ2 =(TextView) findViewById(R.id.txtMQ2);
        btn_off =(Button) findViewById(R.id.btn_off);
        btn_on =(Button) findViewById(R.id.btn_on);
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_On_Off.setValue("1");
            }
        });
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_On_Off.setValue("0");
            }
        });
        database_Led_1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.getValue(String.class);

                Log.d(TAG, "Đèn phòng khách: " + status);
                Switch_Led_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            database_Led_1.setValue("ON");
                        }
                        else {
                            database_Led_1.setValue("OFF");
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database_Led_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.getValue(String.class);

                Log.d(TAG, "Đèn ngủ : " + status);
                Switch_led_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            database_Led_2.setValue("ON");
                        }
                        else {
                            database_Led_2.setValue("OFF");
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Database_MQ2 = FirebaseDatabase.getInstance().getReference().child("MQ2");
        Database_MQ2.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String a =dataSnapshot.getValue().toString();
               txtMQ2.setText(a);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

}
