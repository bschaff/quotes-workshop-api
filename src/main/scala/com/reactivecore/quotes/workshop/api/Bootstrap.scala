package com.reactivecore.quotes.workshop.api

import play.core.server.ServerConfig

object Bootstrap extends App {

  private val serverConfig: ServerConfig = ServerConfig()

  private val service = QuotesWorkshopApiService(serverConfig = serverConfig)

  Runtime.getRuntime.addShutdownHook(new Thread(new Runnable {
    override def run(): Unit = service.stop()
  }))

}
