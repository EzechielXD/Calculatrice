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
    var isNewOperation = true
    var oldNumber = ""
    var op = "+"
    var moins = false
    var virgule = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText.setTextIsSelectable(true)
        editText.isFocusable = false
        editText.isClickable = false
        editText.isLongClickable = false


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

    fun numberEvent(view: View){
        if(isNewOperation)
            editText.setText("")

        isNewOperation = false
        var buttonClick = editText.text.toString()
        var buttonSelect = view as Button
        var negatif = 0
        when(buttonSelect.id){
            bu0.id -> {buttonClick += "0"}
            bu1.id -> {buttonClick += "1"}
            bu2.id -> {buttonClick += "2"}
            bu3.id -> {buttonClick += "3"}
            bu4.id -> {buttonClick += "4"}
            bu5.id -> {buttonClick += "5"}
            bu6.id -> {buttonClick += "6"}
            bu7.id -> {buttonClick += "7"}
            bu8.id -> {buttonClick += "8"}
            bu9.id -> {buttonClick += "9"}
            buPlusMoins.id -> {
                if(moins == false)
                {
                    buttonClick = "-"+buttonClick
                }
                moins = false

            }

        }
        editText.setText(buttonClick)
    }

    fun operatorEvent(view: View){
        isNewOperation = true
        oldNumber = editText.text.toString()
        var buttonSelect = view as Button
        when(buttonSelect.id){
            buMultiplication.id -> {op = "×"}
            buDivision.id -> {op = "÷"}
            buPlus.id -> {op = "+"}
            buMoins.id -> {op = "-"}
            buModulo.id -> {op = "%"}
        }

    }

    fun equalEvent(view: View){

        var newNumber = editText.text.toString()
        var result = 0.0;
        var result_int : Int;


        when(op){
            "×" -> {
                if((oldNumber.isNotEmpty() && oldNumber.isDigitsOnly()) && (newNumber.isNotEmpty() && newNumber.isDigitsOnly())){
                    result_int = (oldNumber.toDouble() * newNumber.toDouble()).toInt()
                    editText.setText(result_int.toString())
                }else{
                    result = oldNumber.toDouble() * newNumber.toDouble()
                    editText.setText(result.toString())
                }}
            "+" -> {
                if((oldNumber.isNotEmpty() && oldNumber.isDigitsOnly()) && (newNumber.isNotEmpty() && newNumber.isDigitsOnly())){
                    result_int = (oldNumber.toDouble() + newNumber.toDouble()).toInt()
                    editText.setText(result_int.toString())
                }else{
                    result = oldNumber.toDouble() + newNumber.toDouble()
                    editText.setText(result.toString())
                }
            }
            "÷" -> {result = oldNumber.toDouble() / newNumber.toDouble()
                editText.setText(result.toString())}
            "-" -> {
                if((oldNumber.isNotEmpty() && oldNumber.isDigitsOnly()) && (newNumber.isNotEmpty() && newNumber.isDigitsOnly())){
                    result_int = (oldNumber.toDouble() - newNumber.toDouble()).toInt()
                    editText.setText(result_int.toString())
                }else{
                    result = oldNumber.toDouble() - newNumber.toDouble()
                    editText.setText(result.toString())
                }
            }
            "%" -> {
                if((oldNumber.isNotEmpty() && oldNumber.isDigitsOnly()) && (newNumber.isNotEmpty() && newNumber.isDigitsOnly())){
                    result_int = (oldNumber.toDouble() % newNumber.toDouble()).toInt()
                    editText.setText(result_int.toString())
                }else{
                    result = oldNumber.toDouble() % newNumber.toDouble()
                    editText.setText(result.toString())
                }
            }
        }



    }

    fun resetEvent(view: View){
        editText.setText("")
        isNewOperation = true
    }

    fun backspaceEvent(view: View){
        if(editText.text.length > 0){
            var newText = editText.text.substring(0, editText.length() - 1)
            editText.setText(newText);
        }
    }



}

