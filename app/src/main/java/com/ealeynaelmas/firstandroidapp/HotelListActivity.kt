package com.ealeynaelmas.firstandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.hotel_list.*
import kotlinx.android.synthetic.main.hotelview.view.*

class HotelListActivity : AppCompatActivity() {
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_list)

        val intentGetter = intent
        val data = intentGetter.getStringExtra("hotelsData")
        createHotelGroup(data!!)

    }
    fun loadHotel(id:String){
        val intent = Intent(applicationContext, HotelActivity ::class.java)
        intent.putExtra("hotelId", id)
        startActivity(intent)
    }

    fun loadMainPage(view : View){
        val intent = Intent(applicationContext, MainActivity ::class.java)
        startActivity(intent)
    }

    fun createHotelGroup(city:String){
        database.collection("Hotels")
            .whereEqualTo("Sehir", city)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    println("${document.id} => ${document.data["İsim"]}")
                    var view: View = LayoutInflater.from(this).inflate(R.layout.hotelview, null);
                    myLayout2.addView(view);
                    view.hotelName.text = document.data["İsim"].toString()
                    view.cost.text = document.data["Fiyat"].toString() + "tl"
                    view.location.text = document.data["Konum"].toString()
                    view.showHotel.setOnClickListener{
                        loadHotel(document.id);
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "Bu sehire ait otel bulunamadi", Toast.LENGTH_LONG).show()
            }
    }
}