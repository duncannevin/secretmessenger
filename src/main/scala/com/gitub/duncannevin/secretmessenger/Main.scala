package com.gitub.duncannevin.secretmessenger

import com.gitub.duncannevin.secretmessenger.module.Module
import com.google.inject.Guice

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  val injector = Guice.createInjector(new Module)
  injector.getInstance(classOf[Server])
}
