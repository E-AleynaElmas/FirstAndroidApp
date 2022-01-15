package com.ealeynaelmas.firstandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_hotel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_rezervation.*

class RezervationActivity : AppCompatActivity() {
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezervation)
        setTitle("ODABAK")
        println(RezervationData.rezervation.rcheckindate)
        radioButton1.setOnClickListener{
            wishText.visibility = View.VISIBLE
        }
        radioButton2.setOnClickListener{
            wishText.visibility = View.GONE
            wishText.text.clear()
        }
    }

    fun loadMainPage(view : View){
        val intent = Intent(applicationContext, MainActivity ::class.java)
        startActivity(intent)
    }

    fun addHotelToDB(view: View){
        if(!checkAnyEmpty()) {
            val postHashMap = hashMapOf<String, Any>()
            postHashMap.put("OtelIsmi", RezervationData.rezervation.rhotelname)
            postHashMap.put("OtelRuhsatNo", RezervationData.rezervation.rhotellicanceno)

            postHashMap.put("KisiSayisi", RezervationData.rezervation.rpersoncount)
            postHashMap.put("CocukSayisi", RezervationData.rezervation.rchilcount)
            postHashMap.put("GirisTarih", RezervationData.rezervation.rcheckindate)
            postHashMap.put("CikisTarih", RezervationData.rezervation.rcheckoutdate)

            postHashMap.put("IrtibatIsim", ContactName.text.toString())
            postHashMap.put("IrtibatSoyad", ContactLastName.text.toString())
            postHashMap.put("IrtibatEPosta", ContactMail.text.toString())
            postHashMap.put("IrtibatNo", ContactPhone.text.toString())

            postHashMap.put("KisiIsim", PersonName.text.toString())
            postHashMap.put("KisiSoyad", PersonLastName.text.toString())
            postHashMap.put("KisiTC", PersonTC.text.toString())
            postHashMap.put("KisiNo", PersonPhone.text.toString())

            postHashMap.put("ÖzelIstek", radioButton1.isChecked.toString().toBoolean())
            postHashMap.put("ÖzelMesaj", wishText.text.toString())

            database.collection("Rezervations").add(postHashMap).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("basarili")
                    loadMainPage(view)
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                    .show()

            }
        }
    }

    fun checkAnyEmpty(): Boolean{
        return (ContactName.text.isEmpty() || ContactLastName.text.isEmpty() || ContactMail.text.isEmpty()
                || ContactPhone.text.isEmpty() || PersonName.text.isEmpty() || PersonLastName.text.isEmpty()
                || PersonTC.text.isEmpty() || PersonPhone.text.isEmpty())
    }
}