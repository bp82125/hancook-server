package com.hancook.hancookbe.exceptions

class AssociatedEntitiesException(objectName: String, associatedObjectName: String): RuntimeException(
    "Cannot delete $objectName with with associated $associatedObjectName. Maybe try to delete all the $associatedObjectName before delete the $objectName"
)