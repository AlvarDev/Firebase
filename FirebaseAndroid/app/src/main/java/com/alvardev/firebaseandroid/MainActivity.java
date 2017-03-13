package com.alvardev.firebaseandroid;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tv_message) protected TextView tvMessage;
    @BindView(R.id.ti_send_message) protected TextInputEditText tiSendMessage;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setRealTimeDatabase();
    }

    @OnClick(R.id.ib_send_message)
    protected void sendMessage(){
        if(tiSendMessage.getText().toString().length() > 0){
            String message = (tvMessage.getText().toString().length() == 0 ? "" : tvMessage.getText().toString() + "\n") +
                    "Android: " + tiSendMessage.getText().toString();
            // Write a message to the database
            myRef.setValue(message);
            tiSendMessage.setText("");
            hideKeyboard();
        }else{
            Log.i(TAG, "Message is empty");
        }
    }

    private void setRealTimeDatabase(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvMessage.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                tvMessage.setText(":(");
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
