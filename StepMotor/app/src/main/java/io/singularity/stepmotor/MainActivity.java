package io.singularity.stepmotor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ChildEventListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseDatabase firebaseDatabase;
    private Gpio gpio1;
    private Gpio gpio2;
    private Gpio gpio3;
    private Gpio gpio4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManagerService service = new PeripheralManagerService();

        try {
            gpio1 = service.openGpio("BCM6");
            gpio1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            gpio2 = service.openGpio("BCM13");
            gpio2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            gpio3 = service.openGpio("BCM19");
            gpio3.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            gpio4 = service.openGpio("BCM26");
            gpio4.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        } catch (Exception e){
            e.printStackTrace();
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("stepMotor/").addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.d("Test", "onChildChanged : " + dataSnapshot.getValue());

        String isEnable = (String) dataSnapshot.getValue();
        for (int i=0; i<1000; i++){
            setLedEnable(Integer.parseInt(isEnable));
            setLedEnable(-1);
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void setLedEnable(int ledEnable) {
        try {
            switch (ledEnable) {
                case 1:
                    gpio1.setValue(true);
                    gpio4.setValue(true);
                    break;
                case 2:
                    gpio3.setValue(true);
                    break;
                case 3:
                    gpio2.setValue(true);
                    break;
                case 4:
                    gpio2.setValue(true);
                    gpio3.setValue(true);
                    break;
                case -1:
                    gpio1.setValue(false);
                    gpio2.setValue(false);
                    gpio3.setValue(false);
                    gpio4.setValue(false);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
