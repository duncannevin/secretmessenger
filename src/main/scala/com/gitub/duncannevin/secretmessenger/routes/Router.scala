package com.gitub.duncannevin.secretmessenger.routes

import akka.http.scaladsl.server.Route

trait Router {
  def route: Route
}


