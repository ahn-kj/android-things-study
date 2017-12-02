package com.akj.firstandroidthings

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : Activity() {

    val TAG = "MainActivity"

    /**
     * GPIO(General-purpose input/output) 의 이름.
     */
    val gpioName = "BCM6"

    /**
     * GPIO 인스턴스
     */
    var gpio: Gpio? = null

    /**
     * Firebase Reference
     */
    val database = FirebaseDatabase.getInstance().reference

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

            gpio?.let { gpio ->
                // Firebase 에서 bcm6 경로를 읽어온다.
                database.child("bcm6").addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(error: DatabaseError?) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        // Firebase 의 bcm6 데이터가 on 이면 LED 를 켜고 그렇지 않으면 끈다.
                        gpio.value = "on".equals(snapshot?.value.toString())
                    }
                })
            }

        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }
}
