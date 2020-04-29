package com.paniagua.bmi

class Operaciones {

    // Método que convierte libras a kilogramos
    fun lib2kg(lib: Double): Double {
        return (lib * 0.45359237)
    }

    // Método que convierte pies y pulgadas a centímetros
    fun ftinc2cm(ft: Double, inc: Double): Double {
        return ((ft * 0.3048 * 100) + (inc * 2.54))
    }

    // Método que calcula el IMC dado el peso y la estatura en kilogramos
    fun calculaIMC(kg: Double, cm: Double): Double {
        val m = cm / 100
        val res = kg / (m * m)
        return ("%.1f".format(res).toDouble())
    }

    // Método que obtiene la categoría a la que pertenece el IMC calculado
    fun obtenerCategoría (imc: Double): String {
        val categoría: String

        if (imc < 16) {
            categoría = "Bajo peso - Delgadez severa"
        } else {
            if (imc in 16.0..16.99) {
                categoría = "Bajo peso - Delgadez moderada"
            } else {
                if (imc in 17.0..18.49) {
                    categoría = "Bajo peso - Delgadez leve"
                } else {
                    if (imc in 18.5..24.99) {
                        categoría = "Normal"
                    } else {
                        if (imc in 25.0..29.99) {
                            categoría = "Sobrepeso - Preobesidad"
                        } else {
                            if (imc in 30.0..34.99) {
                                categoría = "Obesidad leve"
                            } else {
                                if (imc in 35.0..39.99) {
                                    categoría = "Obesidad media"
                                } else {
                                    categoría = "Obesidad mórbida"
                                }
                            }
                        }
                    }
                }
            }
        }

        return categoría
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