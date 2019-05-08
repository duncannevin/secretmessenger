package com.gitub.duncannevin.secretmessenger.module

import akka.actor.ActorSystem
import com.gitub.duncannevin.secretmessenger.Server
import com.gitub.duncannevin.secretmessenger.config.{Factory, ServerConfig, SiteLocationConfig}
import com.gitub.duncannevin.secretmessenger.routes.HomeRouter
import com.google.inject.{AbstractModule, Inject, Provides}
import com.sandinh.akuice.AkkaGuiceSupport

import scala.concurrent.ExecutionContext

class Module @Inject()(implicit val ec: ExecutionContext)
  extends AbstractModule
    with AkkaGuiceSupport {
  override def configure(): Unit = {
    // actor system
    bind(classOf[ActorSystem]).toInstance(ActorSystem("secretmessenger"))
    // server
    bind(classOf[Server]).asEagerSingleton()
    // routers
    bind(classOf[HomeRouter]).asEagerSingleton()
  }

  @Provides
  def provideSiteLocationConfig(configFactory: Factory): SiteLocationConfig = configFactory.siteLocationConfig

  @Provides
  def serverConfig(configFactory: Factory): ServerConfig = configFactory.serverConfig
}
