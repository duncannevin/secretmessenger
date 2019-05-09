package com.gitub.duncannevin.secretmessenger.actors

import akka.NotUsed
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.Flow
import com.gitub.duncannevin.secretmessenger.convert.GetActorFlow
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration
import scala.concurrent.duration.FiniteDuration

object ChatActor {
  private final type RoomFlow = Flow[Message, TextMessage.Strict, _]

  final case class Subscribe(roomId: String)

  def props(actorSystem: ActorSystem): Props = Props(new ChatActor(actorSystem))
}

class ChatActor(actorSystem: ActorSystem) extends Actor with ActorLogging {
  import ChatActor._
  implicit val timeout: Timeout = Timeout(FiniteDuration(3, duration.SECONDS))

  private def createRoom(roomId: String,
                         sender: ActorRef,
                         rooms: Map[String, RoomFlow]): Unit = {
    val roomActor = actorSystem.actorOf(RoomActor.props(roomId))
    for {
      flow <- (roomActor ? GetActorFlow())
        .mapTo[Flow[Message, TextMessage.Strict, _]]
    } yield {
      val addedFlow = rooms + (roomId -> flow)
      context.become(receive(addedFlow))
      sender ! flow
    }
  }

  override def preStart(): Unit = {
    log.debug("ChatActor starting...")
  }

  override def receive: Receive = receive()

  def receive(
      rooms: Map[String, RoomFlow] = Map.empty[String, RoomFlow]): Receive = {
    case Subscribe(roomId) =>
      rooms.get(roomId) match {
        case Some(flow) => sender ! flow
        case None       => createRoom(roomId, sender, rooms)
      }
  }
}
