package com.ealeynaelmas.firstandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_show_rezervation.*

class ShowRezervation : AppCompatActivity() {
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_rezervation)

        val intentGetter = intent
        val data = intentGetter.getStringExtra("RezervationId")
        loadRezervationInfo(data!!)
        disableAllText()
    }

    fun loadRezervationInfo(id:String){
        val docRef = database.collection("Rezervations").document(id)
        val source = Source.CACHE
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var document = task.result

                xNameSurname.setText(document!!.data!!["IrtibatIsim"].toString() + " " + document!!.data!!["IrtibatSoyad"].toString())
                xCheckInDate.setText(document!!.data!!["GirisTarih"].toString())
                xCheckOutDate.setText(document!!.data!!["CikisTarih"].toString())
                xPersonNumber.setText(document!!.data!!["KisiSayisi"].toString())
                xChildNumber.setText(document!!.data!!["CocukSayisi"].toString())
                xEPosta.setText(document!!.data!!["IrtibatEPosta"].toString())
                xNo.setText(document!!.data!!["IrtibatNo"].toString())
                xPersonNameSurname.setText(document!!.data!!["KisiIsim"].toString() + " " + document!!.data!!["KisiSoyad"].toString())
                xPersonNo.setText(document!!.data!!["KisiNo"].toString())
                xPersonTC.setText(document!!.data!!["KisiTC"].toString())

            } else {
                Toast.makeText(applicationContext, "Otel yüklenemedi", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setEnable(view:View){
        xNameSurname.inputType = InputType.TYPE_CLASS_TEXT
        xCheckInDate.inputType = InputType.TYPE_CLASS_TEXT
        xCheckOutDate.inputType = InputType.TYPE_CLASS_TEXT
        xPersonNumber.inputType = InputType.TYPE_CLASS_NUMBER
        xChildNumber.inputType = InputType.TYPE_CLASS_NUMBER
        xEPosta.inputType = InputType.TYPE_CLASS_TEXT
        xNo.inputType = InputType.TYPE_CLASS_NUMBER
        xPersonNameSurname.inputType = InputType.TYPE_CLASS_TEXT
        xPersonNo.inputType = InputType.TYPE_CLASS_NUMBER
        xPersonTC.inputType = InputType.TYPE_CLASS_NUMBER
    }

    fun setDisable(view:View){
       disableAllText()
    }

    fun disableAllText(){
        xNameSurname.inputType = InputType.TYPE_NULL
        xCheckInDate.inputType = InputType.TYPE_NULL
        xCheckOutDate.inputType = InputType.TYPE_NULL
        xPersonNumber.inputType = InputType.TYPE_NULL
        xChildNumber.inputType = InputType.TYPE_NULL
        xEPosta.inputType = InputType.TYPE_NULL
        xNo.inputType = InputType.TYPE_NULL
        xPersonNameSurname.inputType = InputType.TYPE_NULL
        xPersonNo.inputType = InputType.TYPE_NULL
        xPersonTC.inputType = InputType.TYPE_NULL
    }
}