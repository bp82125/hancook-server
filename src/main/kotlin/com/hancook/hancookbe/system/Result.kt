package com.hancook.hancookbe.system

/**
 * This class defines the schema of the response. It is used to encapsulate data prepared by
 * the server side, this object will be serialized to JSON before sent back to the client end.
 */
class Result {
    var isFlag: Boolean = false // Two values: true means success, false means not success

    var code: Int? = null // Status code. e.g., 200

    var message: String? = null // Response message

    var data: Any? = null // The response payload


    constructor()

    constructor(flag: Boolean, code: Int?, message: String?) {
        this.isFlag = flag
        this.code = code
        this.message = message
    }

    constructor(flag: Boolean, code: Int?, message: String?, data: Any?) {
        this.isFlag = flag
        this.code = code
        this.message = message
        this.data = data
    }
}
