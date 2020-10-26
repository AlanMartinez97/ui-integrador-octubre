package security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import exception.api.TokenNotFoundException
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider
import org.ui.Author

class AuthorGenerator : JWTGenerator<Author> {
    override fun generate(author: Author, algorithm: Algorithm): String {
        val token = JWT.create().withClaim("id", author.id)
        return token.sign(algorithm)
    }
}

class MediumTokenJWT(){

    val algorithm = Algorithm.HMAC256("I.Solemnly.Swear.That.I.Am.Up.To.No.Good")
    val generator = AuthorGenerator()
    val verifier = JWT.require(algorithm).build()
    val provider = JWTProvider(algorithm, generator, verifier)

    fun generateToken(author: Author) : String{
        return provider.generateToken(author)
    }

    fun validate(token: String): String {
        val validatedToken = provider.validateToken(token)
        if(!validatedToken.isPresent) throw TokenNotFoundException()
        return validatedToken.get().getClaim("id").asString()
    }
}