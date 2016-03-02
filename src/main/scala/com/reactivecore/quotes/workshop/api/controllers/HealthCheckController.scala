package com.reactivecore.quotes.workshop.api.controllers

import play.api.mvc.{Action, Results}

class HealthCheckController {

  def check = Action(Results.Ok)

}

object HealthCheckController {

  def apply(): HealthCheckController = new HealthCheckController

}
