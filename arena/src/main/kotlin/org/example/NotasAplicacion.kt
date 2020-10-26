package org.example

import org.example.appModels.LoginAppModel
import org.example.windows.login.LoginWindow
import org.ui.bootstrap.getMediumSystem
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class NotasAplicacion(): Application(){

    override fun createMainWindow(): Window<*> {
        var mediumSystem = getMediumSystem()
        return LoginWindow(this, LoginAppModel(mediumSystem))
    }
}

fun main(){
    NotasAplicacion().start()
}