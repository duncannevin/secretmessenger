package com.gitub.duncannevin.secretmessenger.actors

import akka.actor.{ActorLogging, Props}
import akka.http.scaladsl.model.StatusCodes
import com.gitub.duncannevin.secretmessenger.convert.WSConvertFlow
import com.gitub.duncannevin.secretmessenger.entities.{WSRequest, WSResponse}
import com.gitub.duncannevin.secretmessenger.marshelling.Encode
import io.circe.syntax._

object RoomActor {
  final case class Talk(message: Option[String])

  def props(roomId: String): Props = Props(new RoomActor(roomId))
}

class RoomActor(roomId: String)
    extends WSConvertFlow
    with ActorLogging
    with Encode {
  import RoomActor._

  override def preStart(): Unit = {
    log.debug(s"Starting RoomActor: $roomId")
  }

  override def convert(wsRequest: WSRequest): Any = wsRequest match {
    case WSRequest(action, message) if action == "talk" => self ! Talk(message)
    case _                                              => log.warning("Unrecognized message sent to room.")
  }

  override def websocketReceive: Receive = {
    case Talk(message) =>
      out ! WSResponse(StatusCodes.OK.intValue, message).asJson.toString
    case _ => log.warning("Unrecognized in room receive.")
  }
}
