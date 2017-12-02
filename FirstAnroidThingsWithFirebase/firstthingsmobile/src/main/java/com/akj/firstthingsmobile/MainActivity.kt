package com.akj.firstthingsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Firebase 에서 bcm6 값을 읽어 값이 on 이면 스위치를 켜고 아닌 경우 스위치를 끈다.
        database.child("bcm6").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {
                Log.e("FirebaseError", error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot?) {

                snapshot?.let {
                    Log.d("bcm6", it.value.toString())
                    bcm6Switch.isChecked = "on".equals(it.value.toString())
                }
            }
        })

        bcm6Switch.setOnClickListener {
            // 스위치가 클릭되면 스위치의 값에 따라 Firebase 의 bcm6 값을 on 또는 off 로 바꾼다.
            database.child("bcm6").setValue(if(bcm6Switch.isChecked) "on" else "off")
        }
    }
}
