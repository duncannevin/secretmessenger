package com.gitub.duncannevin.secretmessenger.config

import java.net.URL

import com.google.inject.Inject
import com.typesafe.config.{Config, ConfigFactory}

class Factory @Inject()() {
  private val appName = "secretmessenger"
  private val config: Config = ConfigFactory.load()

  private def getConfig(path: String): String = {
    config.getString(path)
  }

  private def getConfig[T](path: String, converter: String => T): T = {
    converter(sys.env.getOrElse(path.replace(".", "_"), getConfig(path)))
  }

  val siteLocationConfig: SiteLocationConfig = SiteLocationConfig(
    url = getConfig(s"$appName.location.url", new URL(_))
  )

  val serverConfig: ServerConfig = ServerConfig(
    host = getConfig(s"$appName.server.host", _.toString),
    port = getConfig(s"$appName.server.port", _.toInt)
  )
}
