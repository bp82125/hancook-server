package com.hancook.hancookbe.exceptions

class ElementNotFoundException(objectName: String, id: String) : RuntimeException(
    "$objectName with the ID: $id was not found"
)