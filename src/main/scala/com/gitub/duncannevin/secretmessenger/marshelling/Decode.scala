package com.gitub.duncannevin.secretmessenger.marshelling

import com.gitub.duncannevin.secretmessenger.entities.{WSRequest, WSResponse}
import io.circe._
import io.circe.generic.semiauto._

trait Decode {
  implicit val wsRequestDecode: Decoder[WSRequest] = deriveDecoder[WSRequest]
  implicit val wsResponseDecode: Decoder[WSResponse] = deriveDecoder[WSResponse]
}
