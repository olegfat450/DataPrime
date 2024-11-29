package com.example.dataprime

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable
import java.time.LocalDate
import java.util.Date

class MainActivity : AppCompatActivity() {

         private lateinit var name: EditText
         private lateinit var surname: EditText
         private lateinit var phone: EditText
         private lateinit var day: EditText
         private lateinit var month: EditText
         private lateinit var year: EditText
         private lateinit var button: Button
         private lateinit var image: ImageView

         private lateinit var photoPickerLauncher: ActivityResultLauncher<Intent>

         var imageUri: Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

              photoPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                                    result -> if( result.resultCode == Activity.RESULT_OK) { imageUri = result.data?.data;image.setImageURI(imageUri) } }
         name = findViewById(R.id.nameText)
         surname = findViewById(R.id.surnameText)
         phone = findViewById(R.id.phoneText)
         day = findViewById(R.id.dayText)
         month = findViewById(R.id.monthText)
         year = findViewById(R.id.yearText)
         button = findViewById(R.id.button)
         image = findViewById(R.id.imageView)

         image.setImageResource(R.drawable.wanted)

                 val photo = Intent(Intent.ACTION_PICK)
                     photo.type = "image/*"

             image.setOnClickListener { photoPickerLauncher.launch(photo) }
            button.setOnClickListener {
                   val date: LocalDate

                    if (name.text.isEmpty() or surname.text.isEmpty()) { message("Не все поля заполнены"); return@setOnClickListener}
                     try {  date = LocalDate.of(year.text.toString().toInt(),month.text.toString().toInt(),day.text.toString().toInt()) } catch (_:Exception) { message("Неверная дата"); return@setOnClickListener }
                    if ((date > LocalDate.now()) or ((LocalDate.now().year - date.year) > 100)) { message("Некорректная дата"); return@setOnClickListener }


                val person = Person(surname.text.toString(),name.text.toString(),phone.text.toString(),date,imageUri.toString())

                  val intent = Intent(this,Activity2::class.java)
                   intent.putExtra(Person::class.java.simpleName,person)
                startActivity(intent)
              }

    }
    fun message(message: String) = Toast.makeText( applicationContext,message,Toast.LENGTH_LONG).show()
}







   class Person( val surname: String?, val name: String?, val phone: String?, val date: LocalDate?, val image: String?): Serializable










