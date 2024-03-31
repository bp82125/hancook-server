package com.hancook.hancookbe.exceptions

import java.util.*

class EntityAlreadyAssociatedException(entityName: String, entityId: String, associatedEntityName: String, associatedEntityId: String)
    : RuntimeException("The $entityName with ID $entityId is already associated with $associatedEntityName with ID $associatedEntityId")