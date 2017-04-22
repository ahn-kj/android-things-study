package singularity.io.iosingularityledwithfirebase;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ChildEventListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int INTERVAL_BETWEEN_BLINKS_MS = 1000;
    private static final String LED = "BCM6";

    private Handler mHandler = new Handler();
    private Gpio mLedGpio;
    private FirebaseDatabase firebaseDatabase;

    private Runnable mBlinkRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLedGpio == null) {
                return;
            }
            try {
                // Toggle the GPIO state
                mLedGpio.setValue(!mLedGpio.getValue());
                Log.d(TAG, "State set to " + mLedGpio.getValue());
                mHandler.postDelayed(mBlinkRunnable, INTERVAL_BETWEEN_BLINKS_MS);
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManagerService service = new PeripheralManagerService();

        try {
            mLedGpio = service.openGpio(LED);
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            Log.i(TAG, "Start blinking LED GPIO pin");

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference("ledController/").addChildEventListener(this);
    }

    private void setLedEnable(boolean isEnable) {
        if (isEnable) {
            mHandler.post(mBlinkRunnable);
        } else {
            mHandler.removeCallbacks(mBlinkRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mBlinkRunnable);
        Log.i(TAG, "Closing LED GPIO pin");
        try {
            mLedGpio.close();
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        } finally {
            mLedGpio = null;
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d("Test", "onChildAdded : " + dataSnapshot.getValue());

        boolean isEnable = (boolean) dataSnapshot.getValue();
        setLedEnable(isEnable);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.d("Test", "onChildChanged : " + dataSnapshot.getValue());

        boolean isEnable = (boolean) dataSnapshot.getValue();
        setLedEnable(isEnable);
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
}
