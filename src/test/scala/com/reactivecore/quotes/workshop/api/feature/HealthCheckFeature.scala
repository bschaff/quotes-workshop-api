package com.reactivecore.quotes.workshop.api.feature

import com.reactivecore.quotes.workshop.api.QuotesWorkshopApiService
import org.scalatest.concurrent.{PatienceConfiguration, ScalaFutures}
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import play.api.http.Status

class HealthCheckFeature extends FeatureSpec with GivenWhenThen with Matchers with ScalaFutures with PatienceConfiguration {

  override implicit val patienceConfig = PatienceConfig(
    timeout = scaled(Span(1, Seconds))
  )

  feature("health check") {
    scenario("service is healthy") {
      Given("a service")
      val service = QuotesWorkshopApiService()

      When("getting the /health endpoint")
      val response = get(s"http://localhost:${service.port}/health").futureValue

      Then("the response status should be OK")
      response.status should be(Status.OK)

      service.stop()
    }
  }

}
