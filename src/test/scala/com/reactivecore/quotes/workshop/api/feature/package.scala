package com.reactivecore.quotes.workshop.api

import play.api.libs.ws.WSResponse
import play.api.libs.ws.ning.NingWSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

package object feature {

  def get(url: String): Future[WSResponse] = {
    implicit val client = NingWSClient()

    client.url(url).get().andThen {
      case response =>
        client.close()
        response
    }
  }

  def options(url: String): Future[WSResponse] = {
    implicit val client = NingWSClient()

    client.url(url).options().andThen {
      case response =>
        client.close()
        response
    }
  }

}
