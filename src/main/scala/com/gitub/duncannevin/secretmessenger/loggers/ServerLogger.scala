package com.gitub.duncannevin.secretmessenger.loggers

trait ServerLogger extends Logger {
  def logSuccessfulStart(port: Int): Unit =
    logger.info(s"$appName listening on port: $port")
  def logFailedStart(msg: String): Unit =
    logger.warn(s"$appName failed to start: $msg")
}
