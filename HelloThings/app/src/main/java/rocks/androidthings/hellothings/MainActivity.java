package rocks.androidthings.hellothings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final int INTERVAL_BETWEEN_BLINKS_MS = 1000;

    /**
     * <pre>
     * GPIO(General-purpose input/output) 의 이름. 라즈베리파이에서 GPIO 및 핀 아웃은 다음 이미지 참조.
     * <img src="https://developer.android.com/things/images/pinout-raspberrypi.png" />
     * </pre>
     */
    private static final String LED = "BCM6";

    private Handler mHandler = new Handler();
    private Gpio mLedGpio;

    /**
     * 1초 마다 LED 를 ON/OFF 하는 Runnable
     */
    private Runnable mBlinkRunnable = new Runnable() {
        @Override
        public void run() {
            if (mLedGpio == null) {
                return;
            }
            try {
                // LED GPIO 의 값을 현재 값의 반대로 설정
                mLedGpio.setValue(!mLedGpio.getValue());
                Log.d(TAG, "State set to " + mLedGpio.getValue());

                // 타이머처럼 동작하기 위해 mBlinkRunnable 을 postDelayed
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

        // PeripheralManagerService 는 주변장치 관리자 같은거
        PeripheralManagerService service = new PeripheralManagerService();

        // gpio 현재 하드웨어에 존재하는 GPIO 리스트를 로깅
        Log.d(TAG, "Available GPIO: " + service.getGpioList());

        try {
            // PeripheralManagerService 를 이용해 LED GPIO 를 오픈
            mLedGpio = service.openGpio(LED);
            // 일단 입출력 설정을 체크한다.
            // DIRECTION_OUT_INITIALLY_LOW 는 GPIO 에서 출력이 가능한가 체크하는것으로 추정.
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            Log.i(TAG, "Start blinking LED GPIO pin");

            // 핸들러로 1초 마다 LED 를 ON/OFF 하는 runnable post
            mHandler.post(mBlinkRunnable);
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mBlinkRunnable 동작 멈춤
        mHandler.removeCallbacks(mBlinkRunnable);
        Log.i(TAG, "Closing LED GPIO pin");

        try {
            // GPIO 닫음. 닫지 않는 경우 다른곳에서 열수 없음.
            mLedGpio.close();
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        } finally {
            mLedGpio = null;
        }
    }
}