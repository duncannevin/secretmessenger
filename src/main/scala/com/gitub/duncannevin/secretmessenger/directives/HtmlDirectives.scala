package com.gitub.duncannevin.secretmessenger.directives

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.{Directives, StandardRoute}
import play.twirl.api.HtmlFormat

trait HtmlDirectives extends Directives {
  def toHtml(page: HtmlFormat.Appendable): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, page.toString()))
  }

  def redirectTo(route: String): StandardRoute = {
    redirect(route, StatusCodes.SeeOther)
  }
}
