package com.ealeynaelmas.firstandroidapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_hotel.*
import kotlinx.android.synthetic.main.hotel_list.view.*
import java.util.*

class AddHotel : AppCompatActivity() {
    lateinit var ImageUri1 : Uri
    lateinit var ImageUri2 : Uri
    lateinit var ImageUri3 : Uri
    lateinit var ImageUri4 : Uri

    var Image1Url : String = ""
    var Image2Url : String = ""
    var Image3Url : String = ""
    var Image4Url : String = ""

    lateinit var storage : FirebaseStorage
    var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)
        setTitle("ODABAK")

        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
    }

    fun loadMainPageView(view : View){
        loadMainPage()
    }

    fun loadMainPage(){
        val intent = Intent(applicationContext, MainActivity ::class.java)
        startActivity(intent)
    }

    fun selectImage(view: View){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        if(view.id == UploadImageButton1.id){
            startActivityForResult(intent, 1)
        }
        else if(view.id == UploadImageButton2.id){
            startActivityForResult(intent, 2)

        }
        else if(view.id == UploadImageButton3.id){
            startActivityForResult(intent, 3)

        }
        else if(view.id == UploadImageButton4.id){
            startActivityForResult(intent, 4)

        }
        view.visibility = View.INVISIBLE;
    }

    fun shareImage(view: View) {
        val reference = storage.reference

        var uuid = UUID.randomUUID()
        var imageName = "${uuid}.jpg"
        var imageReference = reference.child("images").child(imageName)

        imageReference.putFile(ImageUri1!!).addOnSuccessListener { taskSnapShot ->
            val iReference1 =
                FirebaseStorage.getInstance().reference.child("images").child(imageName)
            iReference1.downloadUrl.addOnSuccessListener { uri1 ->
                Image1Url = uri1.toString()

                var uuid2 = UUID.randomUUID()
                var imageName2 = "${uuid2}.jpg"
                var imageReference2 = reference.child("images").child(imageName2)

                imageReference2.putFile(ImageUri2!!).addOnSuccessListener { taskSnapShot ->
                    val iReference2 =
                        FirebaseStorage.getInstance().reference.child("images").child(imageName2)
                    iReference2.downloadUrl.addOnSuccessListener { uri2 ->
                        Image2Url = uri2.toString()

                        var uuid3 = UUID.randomUUID()
                        var imageName3 = "${uuid3}.jpg"
                        var imageReference3 = reference.child("images").child(imageName3)

                        imageReference3.putFile(ImageUri3!!).addOnSuccessListener { taskSnapShot ->
                            val iReference3 =
                                FirebaseStorage.getInstance().reference.child("images").child(imageName3)
                            iReference3.downloadUrl.addOnSuccessListener { uri3 ->
                                Image3Url = uri3.toString()

                                var uuid4 = UUID.randomUUID()
                                var imageName4 = "${uuid4}.jpg"
                                var imageReference4 = reference.child("images").child(imageName4)

                                imageReference4.putFile(ImageUri4!!).addOnSuccessListener { taskSnapShot ->
                                    val iReference4 =
                                        FirebaseStorage.getInstance().reference.child("images").child(imageName4)
                                    iReference4.downloadUrl.addOnSuccessListener { uri4 ->
                                        Image4Url = uri4.toString()

                                        addHotelToDB()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            ImageUri1 = data?.data!!
            HotelImage1.setImageURI(ImageUri1)

            HotelImage1.visibility = View.VISIBLE
            CloseButton1.visibility = View.VISIBLE
        }

        else if(requestCode == 2 && resultCode == RESULT_OK){
            ImageUri2 = data?.data!!
            HotelImage2.setImageURI(ImageUri2)

            HotelImage2.visibility = View.VISIBLE
            CloseButton2.visibility = View.VISIBLE
        }

        else if(requestCode == 3 && resultCode == RESULT_OK){
            ImageUri3 = data?.data!!
            HotelImage3.setImageURI(ImageUri3)

            HotelImage3.visibility = View.VISIBLE
            CloseButton3.visibility = View.VISIBLE
        }

        else if(requestCode == 4 && resultCode == RESULT_OK){
            ImageUri4 = data?.data!!
            HotelImage4.setImageURI(ImageUri4)

            HotelImage4.visibility = View.VISIBLE
            CloseButton4.visibility = View.VISIBLE
        }
    }

    fun closeButton(view: View){
        if(view.id == CloseButton1.id){
            HotelImage1.visibility = View.INVISIBLE;
            UploadImageButton1.visibility = View.VISIBLE;
        }
        else if(view.id == CloseButton2.id){
            HotelImage2.visibility = View.INVISIBLE;
            UploadImageButton2.visibility = View.VISIBLE;

        }
        else if(view.id == CloseButton3.id){
            HotelImage3.visibility = View.INVISIBLE;
            UploadImageButton3.visibility = View.VISIBLE;
        }
        else if(view.id == CloseButton4.id){
            HotelImage4.visibility = View.INVISIBLE;
            UploadImageButton4.visibility = View.VISIBLE;
        }
        view.visibility = View.INVISIBLE;
    }

    fun addHotelToDB(){
        val postHashMap = hashMapOf<String, Any>()
        postHashMap.put("Sehir", HotelCity.text.toString())
        postHashMap.put("İsim", HotelName.text.toString())
        postHashMap.put("RuhsatNo", HotelData.hotelData.hlicanceno)
        postHashMap.put("BelgeNo", HotelData.hotelData.hdocumentno)
        postHashMap.put("Fiyat", HotelCost.text.toString().toInt())
        postHashMap.put("Konum", HotelLocation.text.toString())

        postHashMap.put("Foto1", Image1Url)
        postHashMap.put("Foto2", Image2Url)
        postHashMap.put("Foto3", Image3Url)
        postHashMap.put("Foto4", Image4Url)

        postHashMap.put("Ozellik1", Attribute1.isChecked())
        postHashMap.put("Ozellik2", Attribute2.isChecked())
        postHashMap.put("Ozellik3", Attribute3.isChecked())
        postHashMap.put("Ozellik4", Attribute4.isChecked())
        postHashMap.put("Ozellik5", Attribute5.isChecked())
        postHashMap.put("Ozellik6", Attribute6.isChecked())

        database.collection("Hotels").add(postHashMap).addOnCompleteListener{task ->
            if(task.isSuccessful){
                println("basarili")
                loadMainPage()
            }
        }.addOnFailureListener{exception->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()

        }

    }
}