package model

import exception.model.AuthorNotFoundException
import exception.model.EmailAlreadyInUseException
import exception.model.NotFoundException
import org.ui.*
import org.ui.bootstrap.getMediumSystem

class Medium(){
    val system = getMediumSystem()

    fun getAuthorById(authorId: String): Author {
        var author = system.authors.find{ it.id == authorId}
        if(author == null) throw AuthorNotFoundException()

        return author
    }

    fun register(name: String, email: String, passwd : String, image: String): Author{
        if(existsAuthor(email)){
            throw EmailAlreadyInUseException(email)
        }
        return system.registerAuthor(name, email, passwd, image)
    }

    fun login(name: String, password: String) : Author{
        return system.authors.find { it.email == name && it.password == password } ?: throw AuthorNotFoundException()
    }

    private fun existsAuthor(email: String): Boolean{
        return system.authors.any{it.email == email}
    }

    fun getNotesOfAuthor(authorId: String): MutableList<Note>{
        return system.searchNotesByAuthorId(authorId).toMutableList()
    }

    fun getLastAddedNotes():MutableList<Note>{
        return system.latestAddedNotes().toMutableList()
    }

    fun getNoteById(noteId: String):Note{
        var note : Note
        try{
            note = system.getNote(noteId)
        }catch(e: NotFound){
            throw NotFoundException("Note")
        }

        return note
    }

    fun addCommentToNote(noteId: String, authorCommentId: String, body: String){
        var note: Note
        var comment : Comment
        try{
            note = system.getNote(noteId)
            system.addComment(note.id, authorCommentId, DraftComment(body))
        }catch(e: NotFoundException){
            throw NotFoundException("Note")
        }
    }

    fun searchByTitle(searchText: String): MutableList<Note>{
        return system.searchNotesByTitle(searchText).toMutableList()
    }

    fun searchByCategoryName(searchText: String):MutableList<Note>{
        return system.searchNotesByCategory(searchText).toMutableList()
    }
}