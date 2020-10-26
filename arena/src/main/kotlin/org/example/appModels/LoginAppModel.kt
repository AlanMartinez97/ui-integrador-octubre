package org.example.appModels

import org.example.exceptions.CampoRequeridoException
import org.example.exceptions.UsuarioInexistenteException
import org.ui.Author
import org.ui.MediumSystem
import org.ui.bootstrap.getMediumSystem
import org.uqbar.commons.model.annotations.Observable
import sun.security.ec.point.ProjectivePoint

@Observable
class LoginAppModel(mediumSystem: MediumSystem ) {
    var system = mediumSystem
    var userEmail : String = ""
    var userPassword : String = ""
    var autores : MutableList<Author> = system.authors

    init{
        system.registerAuthor("jon", "i@know.nothing", "forTheGuard", "foto.jon")
    }

    fun validarUsuario() : String{
        validarInfoUsuario()
        var res : Boolean

        res = autores.any { it.email.equals(userEmail) &&
                    it.password.equals(userPassword) }

        if(!res){
            throw UsuarioInexistenteException()
        }

        return autores.find { it.email.equals(userEmail) && it.password.equals(userPassword) }!!.id
    }

    private fun validarInfoUsuario(){
        var campoErrores = mutableListOf<String>()

        if(userEmail.isBlank()){
            campoErrores.add("email")
        }

        if(userPassword.isBlank()){
            campoErrores.add("password")
        }

        if(campoErrores.isNotEmpty()){
            throw CampoRequeridoException(campoErrores)
        }
    }
}