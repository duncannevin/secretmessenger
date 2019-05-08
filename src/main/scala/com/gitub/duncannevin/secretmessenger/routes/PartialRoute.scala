package com.gitub.duncannevin.secretmessenger.routes

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives
import akka.util.Timeout

import scala.concurrent.duration.FiniteDuration

trait PartialRoute extends Directives with Router {
  implicit val timeout: Timeout = Timeout(FiniteDuration(5, "seconds"))
  def route: Route
}
