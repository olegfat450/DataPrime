package com.example.dataprime

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import kotlin.math.absoluteValue

class Activity2 : AppCompatActivity() {

     private lateinit var toolbar: Toolbar
     private lateinit var image: ImageView
     private lateinit var surname: TextView
     private lateinit var name: TextView
     private lateinit var phone: TextView
     private lateinit var text1: TextView
     private lateinit var text2: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)

         toolbar = findViewById(R.id.toolbar)
         image = findViewById(R.id.imageTv)
         surname = findViewById(R.id.surnameText)
         name = findViewById(R.id.nameText)
         phone = findViewById(R.id.phoneText)
         text1 = findViewById(R.id.textTv1)
         text2 = findViewById(R.id.textTv2)


        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(getColor(R.color.grey))
        toolbar.setTitleTextColor(Color.WHITE)
        title = "  Карточка данных"

           val person = intent.getSerializableExtra(Person::class.java.simpleName) as Person?
        surname.setText(person?.surname?.replaceFirstChar { it.uppercase() })
        name.setText(person?.name?.replaceFirstChar { it.uppercase() })

        if (person?.phone?.isEmpty() == true) phone.setText("Тел: не указано") else phone.setText("(+7) ${person?.phone}")


        if ( person?.image.toString() == "null" ) image.setImageResource(R.drawable.wanted) else image.setImageURI(person?.image?.toUri())

        val date = person?.date
        val dateNow = LocalDate.now()

        val s: Int =  dateNow.dayOfYear - (date?.dayOfYear ?: 0 )
            val agetemp = dateNow.year - (date?.year ?: 0)
             val age = when {
                                (s >= 0) -> agetemp
                                 else -> agetemp -1}

                     text1.setText("Количество полных лет: ${age}")



         val day = when {
                              (s > 0) -> "${dateNow.plusYears(1).lengthOfYear() - s}"
                              (s < 0) -> "${s.absoluteValue}"
                               else -> "сегодня"
         }

        text2.setText("Дней до дня рождения: ${day}")






    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val bould = AlertDialog.Builder(this)
             bould.setTitle("Дейсвительно выйти?").setCancelable(true)
                 .setNegativeButton("Нет") { d,w -> d.cancel() }
                 .setPositiveButton("Да") { d,w -> finishAffinity() }.create()
        bould.show()

        return super.onOptionsItemSelected(item)
    }



}