package com.gitub.duncannevin.secretmessenger.marshelling

import com.gitub.duncannevin.secretmessenger.entities.WSRequest
import io.circe._, io.circe.generic.semiauto._

trait Decode {
  implicit val fooDecoder: Decoder[WSRequest] = deriveDecoder[WSRequest]
}
