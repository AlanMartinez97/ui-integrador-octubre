package org.example.windows

import org.example.appModels.MediumAppModel
import org.example.appModels.NotasAppModel
import org.example.exceptions.NoSelectionException
import org.example.windows.nota.DeleteNotaDialog
import org.example.windows.nota.FormNotasWindow
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class MediumWindow(owner: WindowOwner, mediumAppModel: MediumAppModel): SimpleWindow<MediumAppModel>(owner, mediumAppModel){

    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption = "Add new note"
            onClick { FormNotasWindow(this@MediumWindow, NotasAppModel(), modelObject).open()}
        }

        Button(actionsPanel) with{
            caption = "Edit note"
            onClick {
                try{
                    modelObject.checkNoteSelection()
                    FormNotasWindow(this@MediumWindow, modelObject.selectedNota!!, modelObject).open()
                }catch(e: NoSelectionException){
                    throw UserException(e.mensaje)
                }
            }
        }

        Button(actionsPanel) with{
            caption = "delete note"
            onClick({
                try{
                    modelObject.checkNoteSelection()
                    DeleteNotaDialog(this@MediumWindow, modelObject).open()
                }catch(e: NoSelectionException){
                    throw UserException(e.mensaje)
                }
            })
        }
    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "Medium"

        Label(mainPanel) with {
            text = "Notas"
            align("center")
        }

        table<NotasAppModel>(mainPanel){
            bindItemsTo("notas")
            bindSelectionTo("selectedNota")
            visibleRows = 2
            column {
                title = "#"
                fixedSize = 50
                bindContentsTo("id")
            }

            column {
                title = "Title"
                fixedSize = 250
                bindContentsTo("title")
            }
        }
    }
}