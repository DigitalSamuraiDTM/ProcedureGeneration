package com.digitalsamurai.opentelemetry.example.auth

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

public fun Routing.postAuthorized(interactor: AuthInteractor, path: String, body: RoutingHandler) {
    post(path) {
        val check = interactor.validate(call)
        if (check == HttpStatusCode.OK) {
            body()
        } else {
            call.respond(check)
        }
    }
}

public fun Routing.getAuthorized(interactor: AuthInteractor, path: String, body: RoutingHandler) {
    get(path) {
        val check = interactor.validate(call)
        if (check == HttpStatusCode.OK) {
            body()
        } else {
            call.respond(check)
        }
    }
}