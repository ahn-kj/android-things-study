package singularity.io.iosingularityledwithfirebase;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public final static int INET4ADDRESS = 1;
    public final static int INET6ADDRESS = 2;

    private static final int INTERVAL_BETWEEN_BLINKS_MS = 1000;
    private static final String LED = "BCM6";
    private static final String LED2 = "BCM26";

    private Handler mHandler = new Handler();
    private Gpio mLedGpio;
    private Gpio mLedGpio2;
    private boolean isToggle;

    private boolean isFirebaseMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ip = getLocalIpAddress(INET4ADDRESS);
        Log.d(TAG, "ip : " + ip);

        TextView ipTextView = (TextView) findViewById(R.id.ipTextView);
        ipTextView.setText(ip);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                d(TAG, "Value is: " + value);
                if ("ON".equals(value)) {
                    isFirebaseMessage = true;
                } else if ("OFF".equals(value)) {
                    isFirebaseMessage = false;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        PeripheralManagerService service = new PeripheralManagerService();

        try {
            mLedGpio = service.openGpio(LED);
            mLedGpio2 = service.openGpio(LED2);

            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mLedGpio2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            Log.i(TAG, "Start blinking LED GPIO pin");
            mHandler.post(mBlinkRunnable);
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    private Runnable mBlinkRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLedGpio == null) {
                return;
            }
            try {
                // Toggle the GPIO state
                if (isToggle) {
                    mLedGpio.setValue(true);
                    mLedGpio2.setValue(false);
                } else {
                    mLedGpio.setValue(false);
                    if (isFirebaseMessage) {
                        mLedGpio2.setValue(true);
                    }
                }

                isToggle = !isToggle;
                d(TAG, "State set to Gpio 6 : " + mLedGpio.getValue());
                mHandler.postDelayed(mBlinkRunnable, INTERVAL_BETWEEN_BLINKS_MS);
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    };

    public static String getLocalIpAddress(int type) {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        switch (type) {
                            case INET6ADDRESS:
                                if (inetAddress instanceof Inet6Address) {
                                    return inetAddress.getHostAddress().toString();
                                }
                                break;
                            case INET4ADDRESS:
                                if (inetAddress instanceof Inet4Address) {
                                    return inetAddress.getHostAddress().toString();
                                }
                                break;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
