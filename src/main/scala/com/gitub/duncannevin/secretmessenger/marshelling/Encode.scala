package com.gitub.duncannevin.secretmessenger.marshelling

import com.gitub.duncannevin.secretmessenger.entities.WSRequest
import io.circe._
import io.circe.generic.semiauto._

trait Encode {
  implicit val fooDecoder: Decoder[WSRequest] = deriveDecoder[WSRequest]
  implicit val fooEncoder: Encoder[WSRequest] = deriveEncoder[WSRequest]
}
