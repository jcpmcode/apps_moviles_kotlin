package com.paniagua.bmi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RadioButton
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

var historia: String = "" //  propiedad que permite almacenar el registro histórico de los calculos de la aplicación.
var firstSuccess = 0 // propiedad auxiliar que permite validar si ya se realizó un cálculo antes de llamar al histórico.

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val operaciones = Operaciones()

        // Método que configura los textView y los textEditView para solicitar de manera clara al usuario que ingrese datos en el sistema métrico
        // También valida si ya estaban llenos los campos con información en el otro sistema de unidades, si es así convierte los datos al sistema métrico
        fun setMetrico() {
            val kg: Double
            val cm: Double
            val lib: Double? = pesoTextInputEditText.text.toString().toDoubleOrNull()
            val ft: Double? = estaturaTextInputEditText1.text.toString().toDoubleOrNull()
            val inc: Double? = estaturaTextInputEditText2.text.toString().toDoubleOrNull()
            pesoTextView.text = getString(R.string.pesoKgTextView)
            estaturaTextView.text = getString(R.string.estaturaCmTextView)
            pesoTextInputEditText.hint = "kilogramos"
            estaturaTextInputEditText1.hint = "centímetros"
            estaturaTextInputEditText2.visibility = View.INVISIBLE
            // Se valida que no estén vacíos los campos para poder realizar la conversión correspondiente
            if (lib != null) {
                kg = operaciones.lib2kg(lib) // Se convierten libras a kilogramos
                pesoTextInputEditText.setText(kg.toString()) // Se muestra el resultado de la conversión
                if (ft != null) {
                    if (inc != null) {
                        cm = operaciones.ftinc2cm(
                            ft,
                            inc
                        ) // Se convierten los pies y pulgadas a centímetros
                        estaturaTextInputEditText1.setText(cm.toString()) // Se muestra el resultado de la conversión
                    }
                }
            }
        }

        // Método que configura los textView y los textEditView para solicitar de manera clara al usuario que ingrese datos en el sistema inglés
        // También valida si ya estaban llenos los campos con información en el otro sistema de unidades, si es así convierte los datos al sistema inglés
        fun setIngles() {
            val lib: Double
            val ft: Int
            val inc: Int
            val kg: Double? = pesoTextInputEditText.text.toString().toDoubleOrNull()
            val cm: Double? = estaturaTextInputEditText1.text.toString().toDoubleOrNull()

            pesoTextView.text = getString(R.string.pesoLbTextView)
            estaturaTextView.text = getString(R.string.estaturaFtInchTextView)
            pesoTextInputEditText.hint = "libras"
            estaturaTextInputEditText1.hint = "pies"
            estaturaTextInputEditText1.imeOptions = EditorInfo.IME_ACTION_NEXT
            estaturaTextInputEditText2.hint = "pulgadas"
            estaturaTextInputEditText2.visibility =
                View.VISIBLE // Se vuelve visible el campo de pulgadas
            // Se valida que no estén vacíos los campos para poder realizar la conversión correspondiente
            if (kg != null) {
                lib = kg / 0.45359237 // Se convierten kilogramos a libras
                pesoTextInputEditText.setText(lib.toString()) // Se muestra el resultado de la conversión
                if (cm != null) {
                    ft = ((cm / 2.54) / 12).toInt() // Se convierten centímetros a pies
                    inc = ((cm / 2.54) % 12).roundToInt() // Se convierten centímetros a pulgadas
                    // Se muestra el resultado de la conversión
                    estaturaTextInputEditText1.setText(ft.toString())
                    estaturaTextInputEditText2.setText(inc.toString())
                }
            }
        }

        // Se selecciona el sistema métrico por default
        setMetrico()

        // listener para validar cual radioButton está seleccionado
        unidadesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)

            if (radio.id == metricoRadioButton.id) {
                setMetrico()
            } else {
                setIngles()
            }
        }
    }

    // Método que calcula el IMC, determina la categoría a la que pertenece, calcula el IMC Prime.
    // Valida que no esté vacío ningún campo y que los datos ingresados permitan realizar los calculos correctamente
    // Si se ingresaron los datos en el sistema Inglés los convierte al sistema métrico para realizar las operaciones
    fun Calcular(@Suppress("UNUSED_PARAMETER") view: View) {
        val operaciones = Operaciones()
        val id: Int =
            unidadesRadioGroup.checkedRadioButtonId // Se obtiene el id del radioButton seleccionado
        val radio: RadioButton =
            findViewById(id) // Se obtiene la instancia del radioButton seleccionado
        var flag: Int // Bandera que permite definir un flujo de acuerdo a los resultados de las validaciones
        var kg = 0.0
        var cm = 0.0

        // Si está seleccionado el sistema métrico se toman los valores directamente de los campos
        if (radio.id == metricoRadioButton.id) {
            // Se valida que no estén vacíos los campos
            if ((pesoTextInputEditText.text.toString() != "") && (estaturaTextInputEditText1.text.toString() != "")) {
                kg = pesoTextInputEditText.text.toString().toDouble()
                cm = estaturaTextInputEditText1.text.toString().toDouble()
                flag = 1
            } else {
                flag = 0
            }
        } else {
            // Si está seleccionado el sistema inglés se valida que no haya ningun campo vacío
            if ((pesoTextInputEditText.text.toString() != "") && (estaturaTextInputEditText1.text.toString() != "") && (estaturaTextInputEditText2.text.toString() != "")) {
                // Se realizan las conversiones de los datos hacia el sistema métrico
                kg = operaciones.lib2kg(pesoTextInputEditText.text.toString().toDouble())
                cm = operaciones.ftinc2cm(
                    estaturaTextInputEditText1.text.toString().toDouble(),
                    estaturaTextInputEditText2.text.toString().toDouble()
                )
                flag = 1
            } else {
                flag = 0
            }
        }
        val imc = operaciones.calculaIMC(kg, cm) // Se obtiene el imc
        // Se valida que no arroje un resultado incorrecto
        if (imc == 0.0 || imc.isNaN() || imc.isInfinite()) {
            flag = 2
        }
        val categoria: String =
            operaciones.obtenerCategoria(imc) // Se determina la categoría del imc
        val intensity: String =
            operaciones.obtenerColor(imc) // Se obtiene el color correspondiente a la categoría
        val prime: Double = operaciones.obtenerPrime(imc) // Se obtiene el imc prime
        val timeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC)
            .format(Instant.now()) // Se obtiene la fecha y hora en que las operaciones fueron realizadas

        // Si la bandera es 0 entonces quedó algún campo vacío
        if (flag == 0) {
            val t = Toast.makeText(this, "No puede quedar ningún campo vacío", LENGTH_LONG)
            t.show()
        } else {
            // Si la bandera es 2 entonces se ingresó algún dato mal
            if (flag == 2) {
                val t = Toast.makeText(
                    this,
                    "ERROR: Por favor revisa tus datos, algo está mal",
                    LENGTH_LONG
                )
                t.show()
            } else {
                // Si la bandera es 1 entonces las validaciones fueron correctas
                firstSuccess = 1 // Se completa con éxito el primer cálculo
                historia += ("""
                    |Fecha y hora: $timeStamp 
                    |IMC: $imc kg/m^2
                    |IMC PRIME: $prime
                    |Categoría: $categoria
                    |
                    |
                    |
        """.trimMargin()) // Se va concatenando a un string la historia de los cálculos
                // Se Inicializa un intent que manda a llamar a la actividad de resultados y se le pasan los datos calculados
                val intent = Intent(this, ResultadosActivity::class.java).apply {
                    putExtra("IMC", imc.toString())
                    putExtra("CATEGORÍA", categoria)
                    putExtra("COLOR", intensity)
                    putExtra("PRIME", prime.toString())
                }
                // Se inicia la actividad
                startActivity(intent)
            }
        }
    }

    fun Registro(@Suppress("UNUSED_PARAMETER") view: View) {

        // Si ya se realizó un cálculo con éxito, entonces se puede consultar el histórico
        if (firstSuccess == 1) {
            // Se Inicializa un intent que manda a llamar a la actividad de resultados y se le pasan la historia
            val intent = Intent(this, RegistroActivity::class.java).apply {
                putExtra("CONJUNTO", historia)
            }
            startActivity(intent)
        } else {
            // De lo contrario se despliega un mensaje de error
            val t =
                Toast.makeText(this, "Necesitas haber realizado al menos un cálculo", LENGTH_LONG)
            t.show()
        }
    }
}