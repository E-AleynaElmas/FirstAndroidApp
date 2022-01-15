package com.ealeynaelmas.firstandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hotel.*
import kotlinx.android.synthetic.main.hotelview.view.*

class HotelActivity : AppCompatActivity() {
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        setTitle("ODABAK")

        val intentGetter = intent
        val data = intentGetter.getStringExtra("hotelId")
        loadHotelInfo(data!!);
    }

    fun loadRezervartion(view : View){
        val intent = Intent(applicationContext, RezervationActivity ::class.java)
        startActivity(intent)
    }

    fun loadMainPage(view : View){
        val intent = Intent(applicationContext, MainActivity ::class.java)
        startActivity(intent)
    }

    fun loadHotelInfo(id:String){
        val docRef = database.collection("Hotels").document(id)
        val source = Source.CACHE
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var document = task.result

                RezervationData.rezervation.rhotelname = document!!.data!!["İsim"].toString();
                RezervationData.rezervation.rhotellicanceno = document!!.data!!["RuhsatNo"].toString().toLong();

                hotelHeader.text = document!!.data!!["İsim"].toString()
                hotelCost.text = document!!.data!!["Fiyat"].toString() + "TL"
                hotelLocation.text = document!!.data!!["Konum"].toString()

                hotelAttr1.isChecked = document!!.data!!["Ozellik1"].toString().toBoolean()
                hotelAttr2.isChecked = document!!.data!!["Ozellik2"].toString().toBoolean()
                hotelAttr3.isChecked = document!!.data!!["Ozellik3"].toString().toBoolean()
                hotelAttr4.isChecked = document!!.data!!["Ozellik4"].toString().toBoolean()
                hotelAttr5.isChecked = document!!.data!!["Ozellik5"].toString().toBoolean()
                hotelAttr6.isChecked = document!!.data!!["Ozellik6"].toString().toBoolean()

                Picasso.get().load(document!!.data!!["Foto1"].toString()).into(hotelImage1)
                Picasso.get().load(document!!.data!!["Foto2"].toString()).into(hotelImage2)
                Picasso.get().load(document!!.data!!["Foto3"].toString()).into(hotelImage3)
                Picasso.get().load(document!!.data!!["Foto4"].toString()).into(hotelImage4)

            } else {
                Toast.makeText(applicationContext, "Otel yüklenemedi", Toast.LENGTH_LONG).show()
            }
        }

    }
}