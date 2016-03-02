package com.reactivecore.quotes.workshop.api.feature

import com.reactivecore.quotes.workshop.api.QuotesWorkshopApiService
import org.scalatest.concurrent.{PatienceConfiguration, ScalaFutures}
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import play.api.http.Status

class OptionsFeature extends FeatureSpec with GivenWhenThen with Matchers with ScalaFutures with PatienceConfiguration {

  override implicit val patienceConfig = PatienceConfig(
    timeout = scaled(Span(1, Seconds))
  )

  feature("options") {
    scenario("requests options of /items") {
      Given("a service")
      val service = QuotesWorkshopApiService()

      When("requesting for the options of /items endpoint")
      val response = options(s"http://localhost:${service.port}/items").futureValue

      Then("the response status should be OK")
      response.status should be(Status.OK)

      service.stop()
    }
  }

}
