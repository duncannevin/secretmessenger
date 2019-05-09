package com.gitub.duncannevin.secretmessenger.routes

import com.google.inject.Inject

class Routes @Inject()(homeRouter: HomeRouter, wsRouter: WSRouter) {
  def routes: Seq[PartialRoute] = Seq(homeRouter, wsRouter)
}
