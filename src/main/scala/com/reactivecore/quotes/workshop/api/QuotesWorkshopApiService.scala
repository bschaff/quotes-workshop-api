package com.reactivecore.quotes.workshop.api

import play.core.server.{NettyServer, ServerConfig}

class QuotesWorkshopApiService(private val serverConfig: ServerConfig) {

  private val routes = Router().routes

  private val server = NettyServer.fromRouter(
    serverConfig.copy(port = serverConfig.configuration.getInt("server.port").orElse(Some(0)))
  )(routes)

  def stop() = {
    server.stop()
  }

  def port: Int = server.httpPort.get

}

object QuotesWorkshopApiService {

  def apply(serverConfig: ServerConfig = ServerConfig()): QuotesWorkshopApiService = {

    val markitOnDemandApi = serverConfig.configuration.getString("markit.on.demand.api.url").get

    new QuotesWorkshopApiService(serverConfig)
  }

}
