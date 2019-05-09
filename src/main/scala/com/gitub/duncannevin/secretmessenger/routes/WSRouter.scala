package com.gitub.duncannevin.secretmessenger.routes

import akka.actor.ActorRef
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.{Directives, Route}
import com.gitub.duncannevin.secretmessenger.directives.{
  MDirectives,
  ValidatorDirectives
}
import com.google.inject.Inject
import javax.inject.{Named, Singleton}
import akka.pattern.ask
import akka.stream.scaladsl.Flow
import com.gitub.duncannevin.secretmessenger.actors.ChatActor.Subscribe
import com.gitub.duncannevin.secretmessenger.codes.ApiError

import scala.util.{Failure, Success}

@Singleton
class WSRouter @Inject()(
    @Named("chat-actor") chatActor: ActorRef
) extends PartialRoute
    with Directives
    with MDirectives
    with ValidatorDirectives {
  override def route: Route = pathPrefix("ws") {
    path("chat") {
      parameter('id) { roomId =>
        val futureFlow = (chatActor ? Subscribe(roomId))
          .mapTo[Flow[Message, TextMessage.Strict, _]]
        onComplete(futureFlow) {
          case Success(flow) => handleWebSocketMessages(flow)
          case Failure(_) =>
            complete(ApiError.wsConnectionFailed.statusCode,
                     ApiError.wsConnectionFailed.message)
        }
      }
    }
  }
}
