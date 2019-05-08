package com.gitub.duncannevin.secretmessenger.routes

import akka.http.scaladsl.server.{Directives, Route}
import com.gitub.duncannevin.secretmessenger.directives.{HtmlDirectives, MDirectives, ValidatorDirectives}

class HomeRouter() extends PartialRoute with Directives with MDirectives with ValidatorDirectives with HtmlDirectives {
  final val appName: String = "Secret Messenger"
  override def route: Route = pathEndOrSingleSlash {
    get {
      toHtml(html.index.render(appName))
    }
  }
}
