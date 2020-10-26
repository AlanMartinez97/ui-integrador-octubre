package org.example.windows.nota

import org.example.appModels.MediumAppModel
import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.text
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class DeleteNotaDialog(owner: WindowOwner, mediumAppModel: MediumAppModel): Dialog<MediumAppModel>(owner, mediumAppModel){
    override fun createFormPanel(dialogoDelete: Panel){
        title = "Delete note dialog"
        Label(dialogoDelete) with {
            text = "Esta seguro de querer eliminar la nota: " + modelObject.selectedNota!!.title + "?"
        }

        Button(dialogoDelete) with {
            caption = "Aceptar"
            onClick({
                modelObject.deleteNota(modelObject.selectedNota!!.id)
                close()
            })
        }

        Button(dialogoDelete) with{
            caption = "Cancelar"
            onClick { {
                close()
            } }
        }
    }
}