package com.akj.firstandroidthings

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : Activity() {

    val TAG = "MainActivity"

    /**
     * LED 를 On / Off 할 딜레이
     */
    val delay = 1000L

    /**
     * GPIO(General-purpose input/output) 의 이름.
     */
    val gpioName = "BCM6"

    /**
     * 안드로이드 타이머
     */
    val timer:Timer = Timer()
    val timerTask:TimerTask? = null

    /**
     * GPIO 인스턴스
     */
    var gpio: Gpio? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // PeripheralManagerService 는 주변장치 관리자 같은거
        val service = PeripheralManagerService()

        // gpio 현재 하드웨어에 존재하는 GPIO 리스트를 로깅
        Log.d(TAG, "Available GPIO: " + service.gpioList)

        try {
            gpio = service.openGpio(gpioName)

            // 입출력 설정을 체크한다.
            // DIRECTION_OUT_INITIALLY_LOW 는 GPIO 를 출력모드로 설정
            gpio?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            // LED 를 ON/OFF 하는 타이머 시작
            timer.schedule(0, delay, {
                gpio?.let { gpio ->
                    // gpio 의 값을 현재값의 반대로 설정
                    gpio.value = !gpio.value
                }
            })

        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }
}
