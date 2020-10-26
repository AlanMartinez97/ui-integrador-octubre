package org.example.exceptions

class CampoRequeridoException(nombresCampo : MutableList<String>) : CustomException(){
    override var mensaje = ""

    init{
        mensaje = "Los siguientes campos son requeridos:" + System.getProperty("line.separator")

        nombresCampo.forEach{
            mensaje += it + System.getProperty("line.separator")
        }
    }
}