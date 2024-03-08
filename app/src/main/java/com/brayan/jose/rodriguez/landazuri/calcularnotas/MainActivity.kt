package com.brayan.jose.rodriguez.landazuri.calcularnotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var notaF : TextView
    private lateinit var nombreX: EditText
    private lateinit var notaX: EditText
    private lateinit var porcentajeN: EditText
    private lateinit var BR: TextView
    private lateinit var guardarN: Button
    private lateinit var agregarN: Button
    private lateinit var resultado: TextView
    private lateinit var barritaA: ProgressBar
   private lateinit var  porcentaje2 : TextView
  private lateinit var  siguienteEstudiante : Button


    var porcentajeAcumulado = 0

    val listaNotas = mutableListOf<Double>()
    val listaPorcentaje = mutableListOf<Int>()

    var estudianteActual = Estudiante()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notaF = findViewById(R.id.notaFinalxd)
        nombreX = findViewById(R.id.NombreX)
        notaX = findViewById(R.id.NotaX)
        porcentajeN = findViewById(R.id.PorcentajeN)
        guardarN = findViewById(R.id.guardarN)
        agregarN = findViewById(R.id.AgregarN)
        BR = findViewById(R.id.BR)
        barritaA = findViewById<ProgressBar>(R.id.barritaA)
        resultado = findViewById(R.id.resultado)
        porcentaje2 = findViewById(R.id.porcentaje2)
        siguienteEstudiante = findViewById(R.id.siguiente_estudiante)




        agregarN.setOnClickListener {

            val nombre = nombreX.text.toString()
            val nota = notaX.text.toString()
            val porcentaje = porcentajeN.text.toString()


            if (validarVacio(nombre, nota, porcentaje)) {
                if (validarNombre(nombre)
                    && validarNota(nota.toDouble())
                    && validarPorcentaje(
                        porcentaje.toInt()
                    )
                ) {

                    listaNotas.add(nota.toDouble())
                    listaPorcentaje.add(porcentaje.toInt())

                    porcentajeAcumulado += porcentaje.toInt()

                    actualizarProgress(porcentajeAcumulado)

                     siguienteEstudiante.isEnabled = false
                    siguienteEstudiante.visibility = View.INVISIBLE



                    nombreX.isEnabled = false
                    notaX.text.clear()
                    porcentajeN.text.clear()


                    Toast.makeText(
                        this,
                        "La nota fue ingresada bien", Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this, "Ingreso los datos mal ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }else{
                Toast.makeText(
                    this, "Ingreso los datos mal ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        guardarN.setOnClickListener {
            estudianteActual.nombre = nombreX.text.toString()
            estudianteActual.notas = listaNotas
            estudianteActual.porcentajes = listaPorcentaje
            resultado.text = estudianteActual.calcularPromedio().toString()
            notaF.text = estudianteActual.notaFinal().toString()
            barritaA.progress = 0

          notaF.visibility = View.VISIBLE
            resultado.visibility = View.VISIBLE
            siguienteEstudiante.isEnabled = true
            siguienteEstudiante.visibility = View.VISIBLE


        //viev visible
        }



        siguienteEstudiante.setOnClickListener {

        guardarN.visibility = View.VISIBLE


         notaF.visibility = View.VISIBLE
         resultado.visibility = View.VISIBLE
         nombreX.text.clear()
         notaX.text.clear()
         porcentajeN.text.clear()
       porcentaje2.text = ""
         nombreX.isEnabled = true
         barritaA.progress = 0
         porcentajeAcumulado = 0
         resultado.text = ""
         notaF.text = ""


     listaNotas.clear()
         listaPorcentaje.clear()
     }


    }



    fun actualizarProgress(porcentaje: Int)  {
        barritaA.progress = porcentaje


        porcentaje2.text = "$porcentaje%"
        if (porcentaje >= 100 ) {
            guardarN.isEnabled = true
        }else{
            guardarN.isEnabled = false
        }

    }

    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }

    fun validarVacio(nombre: String, nota: String, porcentaje: String): Boolean {
        return !nombre.isNullOrEmpty() && !nota.isNullOrEmpty() && !porcentaje.isNullOrEmpty()
    }

    fun validarNombre(nombre: String): Boolean {
        return !nombre.matches(Regex(".*\\d.*"))

    }

    fun validarNota(nota: Double): Boolean {
        return nota >= 0 && nota <= 5
    }

    fun validarPorcentaje(porcentaje: Int): Boolean {
        return porcentajeAcumulado + porcentaje <= 100


    }
}
class Estudiante() {
    var nombre: String = ""
    var notas: List<Double> = listOf()
    var porcentajes: List<Int> = listOf()
///
    fun calcularPromedio(): Double {
        var sumaNotas = 0.0
        for (n in notas) {
            sumaNotas += n
        }

        return sumaNotas / notas.size

    }

    fun notaFinal(): Double {
        var notaF: Double = 0.0
        var contador = 0

        for (n in notas) {
            notaF += (n * porcentajes[contador] / 100)
            contador++
        }
 return Math.round(notaF * 100.00) / 100.00
    }

}