package com.example.calculatrice

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.mathcollection.MathFunctions.mod
import org.mozilla.javascript.tools.debugger.Dim.ContextData
import java.text.DecimalFormat
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class MainActivity : AppCompatActivity() {
    var isNewOp = true
    var oldNumber = ""
    var op = "+"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText.setTextIsSelectable(true)
        editText.isFocusable = false
        editText.isClickable =false
        editText.isLongClickable =false


    }




    fun numberEvent(view: View){
        if(isNewOp)
            editText.setText("")

        isNewOp = false
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
           buPlusMoins.id -> {buttonClick = "-$buttonClick"}


        }
        editText.setText(buttonClick)
    }

    fun operatorEvent(view: View){
        isNewOp = true
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
        editText.setText("0")
        isNewOp = true
    }

    fun backspaceEvent(view: View){
        if(editText.text.length > 0){
            var newText = editText.text.substring(0, editText.length() - 1)
            editText.setText(newText);
        }
    }

    fun copyActionEvent(view: View){
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


