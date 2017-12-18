package com.munteanu.akkademy.tell

import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive
import akka.event.Logging
import com.munteanu.akkademy.messages.{ResponseMsg, RequestMsg}

/**
  * Created by romunteanu on 12/11/17.
  */
class TellServiceActor extends Actor {

  val log = Logging(context.system, this)
  val senderRef = sender()

  override def receive: Receive = {
    case RequestMsg(id, request) =>
      log.info(s"Received request: $request")
      senderRef ! ResponseMsg(id, request + " enriched with some data")
  }
}

object TellServiceActor {
  def props(): Props = Props[TellServiceActor]
}
