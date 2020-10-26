package org.example.appModels

import org.ui.Author
import org.ui.Note
import org.uqbar.commons.model.annotations.Observable

@Observable
class NotasAppModel(model : Note){

    var id : String = ""
    var title : String = ""
    var body : String = ""
    var categories : String = ""
    var model = model

    init {
        id = model.id
        title = model.title
        body = model.body
        categories = model.categories.toMutableList().joinToString()
    }

    constructor(): this(Note(
        "",
        "",
        "",
        mutableListOf(),
        Author("", "", "", "", "")
    ))

    fun updateModel() {
        model.title = title
        model.body = body
        model.categories = categories.split(",").map { it.trim() }
    }

    fun rollbackToModel() {
        this.title = model.title
        this.body = model.body
        this.categories = model.categories.toMutableList().joinToString()
    }
}