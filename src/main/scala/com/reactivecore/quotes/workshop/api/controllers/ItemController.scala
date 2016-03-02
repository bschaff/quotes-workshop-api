package com.reactivecore.quotes.workshop.api.controllers

import org.slf4j.{Logger, LoggerFactory}
import play.api.mvc.{Action, Results}
import play.filters.cors.CORSFilter

class ItemController(private val logger: Logger) {

  def options = {
    CORSFilter() {
      Action(Results.Ok)
    }
  }

}

object ItemController {

  def apply(logger: Logger = LoggerFactory.getLogger(classOf[ItemController])): ItemController = {
    new ItemController(logger)
  }

}
