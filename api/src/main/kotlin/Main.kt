import controller.NoteController
import controller.UserController
import exception.model.EmailAlreadyInUseException
import exception.model.NotFoundException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role
import io.javalin.core.util.RouteOverviewPlugin
import io.javalin.http.BadRequestResponse
import io.javalin.http.ConflictResponse
import io.javalin.http.NotFoundResponse
import model.Medium
import security.MediumAccessManager
import security.MediumTokenJWT

enum class Roles : Role {
    ANYONE, USER
}
fun main(args:Array<String>){
    val medium = Medium()
    val jwtToken = MediumTokenJWT()
    val jwtAccessManager = MediumAccessManager(jwtToken, medium)

    medium.register("juan nieve", "juan@nieve.com.ar", "forTheGuard", "fotos.com.ar/juan")

    val app = Javalin.create{
        it.defaultContentType = "application/json"
        it.registerPlugin(RouteOverviewPlugin("/routes"))
        it.enableCorsForAllOrigins()
        it.accessManager(jwtAccessManager)
    }

    app.before{
        it.header("Access-Control-Expose-Headers", "*")
    }

    app.start(7000)

    val userController = UserController(medium, jwtToken)
    val noteController = NoteController(medium, jwtToken)

    app.routes{
        path("register"){
            post(userController::register, mutableSetOf<Role>(Roles.ANYONE))
        }

        path("login"){
            post(userController::login, mutableSetOf<Role>(Roles.ANYONE))
        }

        path("user"){
            get(userController::getUser, mutableSetOf<Role>(Roles.USER))
            path("notes"){
                get(noteController::getUserNotes, mutableSetOf<Role>(Roles.USER))
            }
        }

        path("content"){
            get(noteController::getLastAddedNotes, mutableSetOf<Role>(Roles.ANYONE))
            path(":id"){
                get(noteController::getNoteById, mutableSetOf<Role>(Roles.ANYONE))
                post(noteController::addCommentToNote, mutableSetOf<Role>(Roles.USER))
            }
        }

        path("search"){
            get(noteController::searchNotes, mutableSetOf<Role>(Roles.ANYONE))
        }

        path("category"){
            path(":name"){
                get(noteController::searchNotesByCategory, mutableSetOf<Role>(Roles.ANYONE))
            }
        }
    }

    app.exception(EmailAlreadyInUseException::class.java){ e, _ ->
        throw ConflictResponse(e.message.toString())
    }


    app.exception(NotFoundException::class.java){ e, _ ->
        throw NotFoundResponse(e.message.toString())
    }
}