package com.munteanu.akkademy

import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by highlander on 4/20/17.
  */
class SClientIntegrationSpec extends FunSpecLike with Matchers {
  val client = new SClient("127.0.0.1:2552")
  describe("SClient") {
    it("should set a value by key and get the value") {
      client.set("foo", "bar")
      val futureResult: Future[Any] = client.get("foo")
      val result = Await.result(futureResult, 10 seconds)
      result should equal("bar")
    }
  }
}
