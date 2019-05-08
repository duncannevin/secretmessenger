package com.gitub.duncannevin.secretmessenger.loggers

import org.apache.logging.log4j.scala.Logging

trait Logger extends Logging {
  val appName: String = "secretmessenger"

  def unrecognizedMessageSentToActor(): Unit =
    logger.info("unrecognized message sent to actor")
}
