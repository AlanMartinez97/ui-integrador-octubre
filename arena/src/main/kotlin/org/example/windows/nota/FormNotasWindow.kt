package org.example.windows.nota

import org.example.appModels.MediumAppModel
import org.example.appModels.NotasAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class FormNotasWindow(owner: WindowOwner, notasAppModel: NotasAppModel, var system: MediumAppModel): SimpleWindow<NotasAppModel>(owner, notasAppModel){

    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption = "accept"
            onClick({
                system.saveOrUpdateNote(modelObject)
                close()
            })
        }

        Button(actionsPanel) with{
            caption = "cancel"
            onClick({
                modelObject.rollbackToModel()
                close()
            })
        }
    }

    override fun createFormPanel(mainPanel: Panel) {
        Label(mainPanel) with {
            text = "Title"
            alignLeft()
        }

        TextBox(mainPanel) with{
            bindTo("title")
            width=250
        }

        Label(mainPanel) with{
            text = "Body"
        }

        TextBox(mainPanel) with{
            bindTo("body")
            width=250
        }

        Label(mainPanel) with{
            text = "Categories"
        }

        TextBox(mainPanel) with{
            bindTo("categories")
            width = 250
        }
    }
}