package com.example.gpa_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {

    private lateinit var subjects: List<EditText>
    private lateinit var credits: List<EditText>
    private lateinit var points: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subjects = listOf(inpSub1, inpSub2, inpSub3, inpSub4, inpSub5)
        credits = listOf(inpCred1, inpCred2, inpCred3, inpCred4, inpCred5)
        points = listOf(inpPoint1, inpPoint2, inpPoint3, inpPoint4, inpPoint5)

        btnOk.setOnClickListener onClick@ {
            var error = false
            var msg = ""
            do {
                run checkLoop@ {

                    if (error || msg.isNotBlank()){
                        println(error.toString() + "subjects")
                        Toast.makeText(
                            this@MainActivity,
                            if (msg.isBlank()) "Please Input All Text Box Before Press OK!!!"
                            else msg,
                            Toast.LENGTH_SHORT
                        ).show()
                        txtTotal.text = ""
                        txtGPA.text = ""
                        return@onClick
                    }

                    subjects.forEach {
                        println(it.text.isNullOrBlank().toString() + "subjects")
                        if (it.text.isNullOrBlank()) {
                            error = true
                            return@checkLoop
                        }
                    }
                    credits.forEach {
                        println(it.text.isNullOrBlank().toString() + "credits")
                        if (it.text.isNullOrBlank()) {
                            error = true
                            return@checkLoop
                        } else if (it.text.toString().toInt() !in 1..3) {
                            msg = "Please input credits only 1 - 3"
                            return@checkLoop
                        }
                    }
                    points.forEach {
                        println(it.text.isNullOrBlank().toString() + "points")
                        if (it.text.isNullOrBlank()) {
                            error = true
                            return@checkLoop
                        } else if (it.text.toString().toDouble() !in doubleArrayOf(4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.0).asList()) {
                            msg = "Please input points only 4, 3.5, 3, 2.5, 2, 1.5, 1 or 0"
                            return@checkLoop
                        }
                    }
                }

            } while(error || msg.isNotBlank())

            val sumCredits = credits.toTypedArray().sumOf { it.text.toString().toInt() }
            txtTotal.text = sumCredits.toString()
            var sumPoint: Double = 0.0

            for ((i, credit) in credits.withIndex()) {
                sumPoint += credit.text.toString().toInt() * points[i].text.toString().toDouble()
            }

            txtGPA.text = (((sumPoint / sumCredits) * 100.0).roundToLong() / 100.0).toString()

        }

        btnClear.setOnClickListener {
            subjects.forEach { it.text.clear() }
            credits.forEach { it.text.clear() }
            points.forEach { it.text.clear() }
            txtTotal.text = ""
            txtGPA.text = ""
        }
    }
}