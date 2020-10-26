package org.example.windows.login

import org.example.appModels.LoginAppModel
import org.example.appModels.MediumAppModel
import org.example.exceptions.CustomException
import org.example.windows.MediumWindow
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class LoginWindow(owner: WindowOwner, loginAppModel: LoginAppModel): SimpleWindow<LoginAppModel>(owner, loginAppModel) {

    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption = "Login"
            width = 300
            onClick({
                try{
                    val loggedAutorId = modelObject.validarUsuario()
                    MediumWindow(owner, MediumAppModel(loggedAutorId, modelObject.system)).open()
                }catch(e : CustomException){
                    throw UserException(e.mensaje)
                }
            })
        }
    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "Login Medium app"

        Label(mainPanel) with {
            text = "email"
            align("left")
        }

        TextBox(mainPanel) with {
            width = 250
            bindTo("userEmail")
        }

        Label(mainPanel) with{
            text = "password"
            align("left")
        }

        TextBox(mainPanel) with {
            width = 250
            bindTo("userPassword")
        }
    }
}