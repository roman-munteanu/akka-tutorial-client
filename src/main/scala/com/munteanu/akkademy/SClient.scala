package com.munteanu.akkademy

import akka.actor.{ActorSelection, ActorSystem}
import akka.util.Timeout

import scala.concurrent.duration._
import akka.pattern.ask
import com.munteanu.akkademy.messages.{GetRequest, SetRequest}

import scala.concurrent.Future
import scala.language.postfixOps

/**
  * Created by highlander on 4/20/17.
  */
class SClient(remoteAddress: String) {
  private implicit val timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("LocalSystem")
  private val remoteDb: ActorSelection =
    system.actorSelection(s"akka.tcp://akkademy-actor-system@$remoteAddress/user/akkademy-db")
  def set(key: String, value: Object): Future[Any] = {
    remoteDb ? SetRequest(key, value)
  }
  def get(key: String): Future[Any] = {
    remoteDb ? GetRequest(key)
  }
}
