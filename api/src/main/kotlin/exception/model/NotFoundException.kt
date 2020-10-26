package exception.model

class NotFoundException(subject: String): Exception("$subject not found")