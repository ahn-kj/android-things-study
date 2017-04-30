package io.singularity.ledwithfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var ledGpio: Gpio? = null

    private val database by lazy { FirebaseDatabase.getInstance() }
    private val gpioRef by lazy { database.getReference("gpio") }
    private val service by lazy { PeripheralManagerService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ledGpioInitialization()
        eventListener()
    }

    /**
     * LED GPIO 초기설정
     *
     */
    private fun ledGpioInitialization() {
        try {
            ledGpio = service.openGpio(LED)
            ledGpio!!.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
            Log.i(TAG, "Start blinking LED GPIO pin")
        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e)
        }
    }

    /**
     * 파이어베이스 이번트 리너스 등록
     * 파이어베이스 디비 gpio (ON이면 GPIO LED 불 들어옴)
     */
    private fun eventListener() {
        gpioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: " + value)
                try {
                    ledGpio?.value = "ON" == value

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    /**
     * 닫을 LED GPIO 추가
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Closing LED GPIO pin")
        try {
            ledGpio?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e)
        } finally {
            ledGpio = null
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val LED = "BCM6"
    }
}
