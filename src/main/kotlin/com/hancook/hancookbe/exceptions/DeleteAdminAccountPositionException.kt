package com.hancook.hancookbe.exceptions

class DeleteAdminAccountPositionException (position: String): RuntimeException("Cannot delete the position where administrative account's position: $position")