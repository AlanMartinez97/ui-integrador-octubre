package controller

import controller.mapper.UserLoginMapper
import controller.mapper.UserRegisterMapper
import exception.model.EmailAlreadyInUseException
import exception.model.AuthorNotFoundException
import io.javalin.http.Context
import model.Medium
import security.MediumTokenJWT

class UserController(val systemHandler: Medium, val tokenJWT: MediumTokenJWT){
    fun register(ctx: Context){
        try{
            val newAuthor = ctx.bodyValidator<UserRegisterMapper>().get()

            val author = systemHandler.register(
                newAuthor.name,
                newAuthor.email,
                newAuthor.password,
                newAuthor.image
            )

            ctx.header("Authorization", tokenJWT.generateToken(author))
            ctx.status(201)
            ctx.json(mapOf("result" to "ok"))
        }catch(e: EmailAlreadyInUseException){
            ctx.status(409)
            ctx.json(
                mapOf(
                    "result" to "error",
                    "message" to e.message.toString()
                )
            )
        }
    }

    fun login(ctx: Context){
        val loginAuthor = ctx.bodyValidator<UserLoginMapper>().get()

        try{
            val author = systemHandler.login(loginAuthor.email, loginAuthor.password)
            ctx.header("Authorization", tokenJWT.generateToken(author))
            ctx.status(200)
            ctx.json(
                mapOf(
                    "result" to "ok"
                )
            )
        }catch(e: AuthorNotFoundException){
            ctx.status(404)
            ctx.json(
                mapOf(
                    "result" to "ok",
                    "message" to e.message.toString()
                )
            )
        }
    }

    fun getUser(ctx: Context){
        val token = ctx.header("Authorization")
        val userId = tokenJWT.validate(token!!)
        val user = systemHandler.getAuthorById(userId)

        ctx.status(200)
        ctx.json(
            UserRegisterMapper(user.name, user.email, user.password, user.photo)
        )
    }
}