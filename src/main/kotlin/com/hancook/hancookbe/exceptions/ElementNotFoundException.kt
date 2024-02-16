package com.hancook.hancookbe.exceptions

import java.util.*

class ElementNotFoundException(objectName: String, id: UUID) : RuntimeException(
    "$objectName with the ID: $id was not found"
)