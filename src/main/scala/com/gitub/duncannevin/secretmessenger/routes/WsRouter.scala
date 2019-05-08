package com.gitub.duncannevin.secretmessenger.routes

import akka.http.scaladsl.server.{Directives, Route}
import com.gitub.duncannevin.secretmessenger.directives.{MDirectives, ValidatorDirectives}

class WsRouter() extends PartialRoute with Directives with MDirectives with ValidatorDirectives {
  override def route: Route = ???
}
