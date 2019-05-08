package com.gitub.duncannevin.secretmessenger.directives

import com.gitub.duncannevin.secretmessenger.codes.ApiError

trait Validator[T] {
  def validate(t: T): Option[ApiError]
}
