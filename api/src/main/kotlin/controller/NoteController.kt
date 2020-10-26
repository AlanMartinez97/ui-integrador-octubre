package controller

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import exception.model.NotFoundException
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import model.Medium
import org.ui.Note
import security.MediumTokenJWT

class NoteController(val systemHandler: Medium, val tokenJWT: MediumTokenJWT){

    fun getUserNotes(ctx: Context){
        val token = ctx.header("Authorization")
        val userId = tokenJWT.validate(token!!)
        val notes = systemHandler.getNotesOfAuthor(userId)

        ctx.status(200)
        ctx.json(
            mapOf(
                "result" to "ok",
                "authorNotes" to notes
            )
        )
    }

    fun getLastAddedNotes(ctx: Context){
        val notes = systemHandler.getLastAddedNotes()

        ctx.status(200)
        ctx.json(
            mapOf(
                "result" to "ok",
                "authorNotes" to notes
            )
        )
    }

    fun getNoteById(ctx: Context){
        val noteId = ctx.pathParam("id")
        val note : Note
        try{
            note = systemHandler.getNoteById(noteId)
        }catch(e: NotFoundException){
            throw NotFoundResponse(e.message.toString())
        }

        ctx.status(200)
        ctx.json(
            mapOf(
                "result" to "ok",
                "note" to note
            )
        )
    }

    fun addCommentToNote(ctx: Context){
        val token = ctx.header("Authorization")
        val userId = tokenJWT.validate(token!!)
        val noteId = ctx.pathParam(":id")
        val requestBody = ObjectMapper().readTree(ctx.body())
        val commentBody = requestBody.get("body").asText()

        systemHandler.addCommentToNote(noteId, userId, commentBody)

        ctx.status(201)
        ctx.json(
            mapOf(
                "result" to "ok"
            )
        )
    }

    fun searchNotes(ctx: Context){
        val searchText = ctx.queryParam("text")?: throw BadRequestResponse("Bad request - texto de busqueda faltante")
        val notes = systemHandler.searchByTitle(searchText)

        ctx.status(200)
        ctx.json(
            mapOf(
                "result" to "ok",
                "notes" to notes
            )
        )
    }

    fun searchNotesByCategory(ctx: Context){
        val category = ctx.pathParam("name")
        val notes = systemHandler.searchByCategoryName(category)

        ctx.status(200)
        ctx.json(
            mapOf(
                "result" to "ok",
                "notes" to notes
            )
        )
    }
}