package org.example.exceptions

import java.lang.Exception

class UsuarioInexistenteException() : CustomException(){
    override var mensaje = "El usuario y/o contrasenia son incorrectas"
}