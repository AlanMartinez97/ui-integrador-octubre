package security

import exception.api.TokenNotFoundException
import exception.model.AuthorNotFoundException
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import model.Medium
import org.ui.Author

class MediumAccessManager(val tokenJWT: MediumTokenJWT, val medium: Medium): AccessManager{

    fun getUser(token: String): Author{
        try{
            val authorId = tokenJWT.validate(token)
            return medium.getAuthorById(authorId)
        }catch(e: TokenNotFoundException){
            throw UnauthorizedResponse("Token not found")
        }catch(e: AuthorNotFoundException){
            throw UnauthorizedResponse("Invalid token")
        }
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when{
            token == null && roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found")
            roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            roles.contains(Roles.USER) -> {
                getUser(token)
                handler.handle(ctx)
            }
        }
    }
}