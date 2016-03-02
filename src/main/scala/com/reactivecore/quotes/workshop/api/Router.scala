package com.reactivecore.quotes.workshop.api

import com.reactivecore.quotes.workshop.api.controllers.{HealthCheckController, ItemController}
import play.api.mvc.{Handler, RequestHeader}
import play.api.routing.sird._

class Router(private val itemController: ItemController) {

  private val healthCheckController = HealthCheckController()

  def routes: PartialFunction[RequestHeader, Handler] = {
    case GET(p"/health") => healthCheckController.check
    case OPTIONS(p"/items") => itemController.options
  }

}

object Router {

  def apply(): Router = {
    new Router(
      ItemController()
    )
  }

}
