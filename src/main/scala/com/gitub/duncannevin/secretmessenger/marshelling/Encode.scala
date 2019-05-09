package com.gitub.duncannevin.secretmessenger.marshelling

import com.gitub.duncannevin.secretmessenger.entities.{WSRequest, WSResponse}
import io.circe._
import io.circe.generic.semiauto._

trait Encode {
  implicit val wsRequestEncode: Encoder[WSRequest] = deriveEncoder[WSRequest]
  implicit val wsResponseEncode: Encoder[WSResponse] = deriveEncoder[WSResponse]
}
