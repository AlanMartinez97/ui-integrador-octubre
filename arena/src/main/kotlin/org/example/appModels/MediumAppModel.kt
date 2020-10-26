package org.example.appModels

import org.example.exceptions.NoSelectionException
import org.ui.DraftNote
import org.ui.MediumSystem
import org.ui.Note
import org.uqbar.commons.model.annotations.Observable

@Observable
class MediumAppModel(autorId : String, mediumSystem: MediumSystem){

    val medium = mediumSystem
    var loggedAutorId = autorId
    var notas : MutableList<NotasAppModel> = medium.notes.filter { it.author.id == autorId }.map{
        NotasAppModel(
            it
        )
    }.toMutableList()
    var selectedNota : NotasAppModel? = null

    fun saveOrUpdateNote(notasAppModel: NotasAppModel) {
        var draft = DraftNote(notasAppModel.title, notasAppModel.body, notasAppModel.categories)
        if(notasAppModel.id != null && notasAppModel.id.isNotBlank()){
            medium.editNote(notasAppModel.id, draft)
        }else{
            medium.addNote(loggedAutorId, draft)
        }

        updateFront()
    }

    private fun updateFront(){
        notas = medium.notes.filter { it.author.id == loggedAutorId }.map{
            NotasAppModel(
                it
            )
        }.toMutableList()
    }

    fun checkNoteSelection(){
        if(selectedNota == null){
            throw NoSelectionException()
        }
    }

    fun deleteNota(idNota : String) {
        medium.notes.removeIf { it.id == idNota }
        updateFront()
    }
}