package com.gitub.duncannevin.secretmessenger.entities

case class WSRequest(action: String, message: Option[String] = None)
