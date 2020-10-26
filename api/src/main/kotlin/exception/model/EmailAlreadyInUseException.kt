package exception.model

class EmailAlreadyInUseException(email: String): Exception("El email: $email ya esta uso.")