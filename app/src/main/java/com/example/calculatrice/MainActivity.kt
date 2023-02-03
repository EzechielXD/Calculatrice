package com.example.calculatrice

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var DernierNombre = ""
    var PremierNombre = ""
    var Operator = ""
    var isOperatorClick = false
    var isEqualClick = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setTitle("Ma super Calco")
        editText.setTextIsSelectable(true)
        editText.isFocusable = false
        editText.isClickable = false
        editText.isLongClickable = false


        bu0.setOnClickListener{numberEvent("0", true)}
        bu1.setOnClickListener{numberEvent("1", true)}

        bu2.setOnClickListener { numberEvent("2", true) }

        bu3.setOnClickListener { numberEvent("3", true) }

        bu4.setOnClickListener { numberEvent("4", true) }

        bu5.setOnClickListener { numberEvent("5", true) }

        bu6.setOnClickListener { numberEvent("6", true) }

        bu7.setOnClickListener { numberEvent("7", true) }

        bu8.setOnClickListener { numberEvent("8", true) }

        bu9.setOnClickListener { numberEvent("9", true) }

        buPlus.setOnClickListener { operatorEvent("+") }

        buMoins.setOnClickListener { operatorEvent("-") }

        buMultiplication.setOnClickListener { operatorEvent("*") }

        buDivision.setOnClickListener { operatorEvent("÷") }

        buModulo.setOnClickListener { operatorEvent("%") }

        buPlusMoins.setOnClickListener { inverse() }

        buegal.setOnClickListener { equalEvent() }

            buDel.setOnClickListener { del() }

                buAC.setOnClickListener { resetEvent() }


        editText.setOnClickListener{
                val result = editText.text.toString()
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Resultat", editText.text.toString())
                if (result.isNotEmpty()){
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this, "Résultat copié dans le papier presse", Toast.LENGTH_SHORT).show()
                }else{
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this, "Aucun resultatà copier", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var text: String
        text = editText.getText().toString()
        outState.putString("key", text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        var newText: String
        newText = savedInstanceState.getString("key").toString()
        editText.setText(newText)
    }


    private fun numberEvent(string: String, canClear: Boolean) {
        if (isOperatorClick || isEqualClick || editText.text.isNotEmpty() && canClear) {
            isOperatorClick = false
            isEqualClick = false
        }
        editText.append(string)
        DernierNombre += string
    }

    private fun operatorEvent(operateur: String) {
        if (Operator!=""){

            equalEvent()
        }
        if (DernierNombre.isNotEmpty()) {
            PremierNombre = DernierNombre
            DernierNombre = ""
            editText.append(operateur)
            Operator = operateur
            isOperatorClick = true
        }
    }

    private fun equalEvent() {
        if (DernierNombre.isNotEmpty() && PremierNombre.isNotEmpty() && Operator.isNotEmpty()) {
            val resultCalcul =calc()
            editText.text = resultCalcul.toString()
            isEqualClick = true
            DernierNombre = resultCalcul.toString()
            PremierNombre = ""
            Operator = ""
        }



    }

    private fun calc(): Any {

        return when (Operator) {
            "+" -> {
                try {
                    PremierNombre.toInt() + DernierNombre.toInt()
                }
                catch (e:java.lang.NumberFormatException){
                    PremierNombre.toDouble() + DernierNombre.toDouble()
                }
            }
            "-" -> {
                try {
                    PremierNombre.toInt() - DernierNombre.toInt()
                } catch (e: java.lang.NumberFormatException) {
                    PremierNombre.toDouble() - DernierNombre.toDouble()
                }
            }
            "*" -> {
                try {
                    PremierNombre.toInt() * DernierNombre.toInt()
                } catch (e: java.lang.NumberFormatException) {
                    PremierNombre.toDouble() * DernierNombre.toDouble()
                }
            }
            "÷" -> {

                val prev = PremierNombre.toDouble()
                val curr = DernierNombre.toDouble()
                val resultat = prev / curr
                if (resultat == resultat.toInt().toDouble()) {
                    resultat.toInt()
                } else {
                    resultat
                }
            }
            "%" ->{
                try {
                    PremierNombre.toInt() % DernierNombre.toInt()
                } catch (e: java.lang.NumberFormatException) {
                    PremierNombre.toDouble() % DernierNombre.toDouble()
                }
            }
            else -> 0
        }
    }

    private fun del() {
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            editText.text = text.substring(0, text.length - 1)
            if (isOperatorClick) {
                Operator = ""
                isOperatorClick = false

            } else {
                val lastChar = text[text.length - 1]
                if(text[0] == '-' && text.length == 2 ){
                    resetEvent() // On retire le signe "-" et le chiffre
                    return
                }

                else if(text.length > 2 && text[text.length - 2] == '-' && text[text.length - 1].isDigit()){
                    editText.text = text.substring(0, text.length - 2)
                    DernierNombre = DernierNombre.substring(0, DernierNombre.length - 1)
                    isOperatorClick = false
                    Operator = ""


                }

                else {
                    if (DernierNombre.length == 0) {
                        DernierNombre = "0"
                        isOperatorClick = false
                    }
                    else  DernierNombre = DernierNombre.substring(0, DernierNombre.length - 1)

                }
            }
        }
    }

    private fun inverse() {
        if (DernierNombre.isNotEmpty()) {
            if (DernierNombre.toIntOrNull() != null) DernierNombre =
                DernierNombre.toInt().times(-1).toString()
            else if (DernierNombre.toDoubleOrNull() != null) DernierNombre =
                DernierNombre.toDouble().times(-1).toString()
            else DernierNombre = DernierNombre.toInt().times(-1).toString()
            if (PremierNombre.isNotEmpty()) {
                editText.text = DernierNombre + Operator + DernierNombre

            } else {
                editText.text = DernierNombre
            }
        } else if (PremierNombre.isNotEmpty()) {
            if (PremierNombre.toIntOrNull() != null) PremierNombre =
                PremierNombre.toInt().times(-1).toString()
            else if (PremierNombre.toDoubleOrNull() != null) PremierNombre =
                PremierNombre.toDouble().times(-1).toString()
            else PremierNombre = PremierNombre.toInt().times(-1).toString()


            editText.text = PremierNombre
        }
    }

    private fun resetEvent() {
        editText.text = ""
        DernierNombre = ""
        PremierNombre = ""
        Operator = ""
        isOperatorClick = false
        isEqualClick = false
    }



    }

