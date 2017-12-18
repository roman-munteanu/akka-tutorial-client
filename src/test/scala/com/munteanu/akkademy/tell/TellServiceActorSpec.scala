package com.munteanu.akkademy.tell

import akka.actor.ActorSystem
import akka.testkit.{TestProbe, TestKit}
import akka.util.Timeout
import com.munteanu.akkademy.messages.{ResponseMsg, RequestMsg}
import org.scalatest.{BeforeAndAfterAll, Matchers, FlatSpecLike}
import akka.pattern.ask

import scala.concurrent.{Await, Future}

import scala.concurrent.duration._

/**
  * Created by romunteanu on 12/12/17.
  */
class TellServiceActorSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with FlatSpecLike
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("tell-service-actor-spec"))

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val tellServiceActor = system.actorOf(TellServiceActor.props())
  implicit val t = 10 seconds
  implicit val timeout: Timeout = 5 seconds

  "TellServiceActor" should
    "respond with an enriched request" in {
      val testProbe = TestProbe()
      val expectedResponse = ResponseMsg("test_ID", "test request enriched with some data")
      tellServiceActor.tell(RequestMsg("test_ID", "test request"), testProbe.ref)
      testProbe.expectMsg(15 seconds, expectedResponse)
    }

}
