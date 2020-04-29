package com.paniagua.bmi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Se lee el extra recibido de la MainActivity y se despliega en el textView
        val conjunto = intent.getStringExtra("CONJUNTO")
        registroTextView.text = conjunto
    }
}
