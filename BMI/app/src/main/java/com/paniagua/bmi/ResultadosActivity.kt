package com.paniagua.bmi

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.resultados_activity.*

class ResultadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultados_activity)

        // Se leen los extras recibidos de la MainActivity y se despliegan en el textView correspondiente
        val imc = intent.getStringExtra("IMC")
        val categoria = intent.getStringExtra("CATEGORÍA")
        val intensity = intent.getStringExtra("COLOR")
        val prime = intent.getStringExtra("PRIME")

        imcTextView.text = imc
        imcTextView.setTextColor(Color.parseColor(intensity))
        categoríaTextView.text = categoria
        categoríaTextView.setTextColor(Color.parseColor(intensity))
        imcPrimeTextView.text = prime
        imcPrimeTextView.setTextColor(Color.parseColor(intensity))
    }
}
