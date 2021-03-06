package com.paniagua.bmi

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class Operaciones {

    // Método que convierte libras a kilogramos
    fun lib2kg(lib: Double): Double {
        return (lib * 0.45359237)
    }

    // Método que convierte pies y pulgadas a centímetros
    fun ftinc2cm(ft: Double, inc: Double): Double {
        return ((ft * 0.3048 * 100) + (inc * 2.54))
    }

    // Método que convierte kilogramos a libras enteras
    fun kg2lib(kg: Double): Int {
        return (kg / 0.45359237).toInt()
    }

    // Método que convierte centímetros a pies enteros
    fun cm2ft(cm: Double): Int {
        return ((cm / 2.54) / 12).toInt()
    }

    // Método que convierte centímetros a pulgadas enteras
    fun cm2inc(cm: Double): Int {
        return ((cm / 2.54) % 12).roundToInt()
    }

    fun obtenerHoraLocal(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")
        return (current.format(formatter))
    }

    // Método que calcula el IMC dado el peso y la estatura en kilogramos
    fun calculaIMC(kg: Double, cm: Double): Double {
        val m = cm / 100
        val res = kg / (m * m)
        return ("%.1f".format(res).toDouble())
    }

    // Método que obtiene la categoría a la que pertenece el IMC calculado
    fun obtenerCategoria (imc: Double): String {
        val categoria: String

        if (imc < 16) {
            categoria = "Bajo peso - Delgadez severa"
        } else {
            if (imc in 16.0..16.99) {
                categoria = "Bajo peso - Delgadez moderada"
            } else {
                if (imc in 17.0..18.49) {
                    categoria = "Bajo peso - Delgadez leve"
                } else {
                    if (imc in 18.5..24.99) {
                        categoria = "Normal"
                    } else {
                        if (imc in 25.0..29.99) {
                            categoria = "Sobrepeso - Preobesidad"
                        } else {
                            if (imc in 30.0..34.99) {
                                categoria = "Obesidad leve"
                            } else {
                                if (imc in 35.0..39.99) {
                                    categoria = "Obesidad media"
                                } else {
                                    categoria = "Obesidad mórbida"
                                }
                            }
                        }
                    }
                }
            }
        }

        return categoria
    }

    // Método que asigna un color representativo a cada categoría
    fun obtenerColor (imc: Double): String {
        val intensity: String

        if (imc < 16) {
            intensity = "#FFBF1010"
        } else {
            if (imc in 16.0..16.99) {
                intensity = "#FFBF7910"
            } else {
                if (imc in 17.0..18.49) {
                    intensity = "#FFE1CF34"
                } else {
                    if (imc in 18.5..24.99) {
                        intensity = "#FF1FBC25"
                    } else {
                        if (imc in 25.0..29.99) {
                            intensity = "#FFE1CF34"
                        } else {
                            if (imc in 30.0..34.99) {
                                intensity = "#FFBF7910"
                            } else {
                                if (imc in 35.0..39.99) {
                                    intensity = "#FFBF7910"
                                } else {
                                    intensity = "#FFBF1010"
                                }
                            }
                        }
                    }
                }
            }
        }

        return intensity
    }

    // Método que obtiene el prime del IMC
    fun obtenerPrime(imc: Double): Double{
        return ("%.2f".format(imc / 25).toDouble())
    }
}