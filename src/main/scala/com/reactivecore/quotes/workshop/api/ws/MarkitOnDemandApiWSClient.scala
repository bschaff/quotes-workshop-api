package com.reactivecore.quotes.workshop.api.ws

import java.util.concurrent.Executors

import play.api.libs.ws.WSClient
import play.api.libs.ws.ning.NingWSClient

import scala.concurrent.ExecutionContext

class MarkitOnDemandApiWSClient(private val wsClient: WSClient,
                                private val markitOnDemandApi: String) {

  private val executionService = Executors.newFixedThreadPool(10)

  private implicit val executionContext = ExecutionContext.fromExecutor(executionService)

  def close(): Unit = {
    wsClient.close()
    executionService.shutdown()
  }

}

object MarkitOnDemandApiWSClient {

  def apply(markitOnDemandApi: String): MarkitOnDemandApiWSClient = {
    new MarkitOnDemandApiWSClient(
      wsClient = NingWSClient(),
      markitOnDemandApi = markitOnDemandApi
    )
  }

}