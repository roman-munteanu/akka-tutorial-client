package com.munteanu.akkademy.tell

import akka.actor.Status.Failure
import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive
import akka.event.Logging
import com.munteanu.akkademy.messages.{SetRequest, GetRequest, RequestMsg, ResponseMsg}

import scala.concurrent.duration._

/**
  * Created by romunteanu on 12/11/17.
  */
class TellClientActor extends Actor {

  import scala.concurrent.ExecutionContext.Implicits.global

  val senderRef = sender()
  val log = Logging(context.system, this)
  val msgMap = scala.collection.mutable.Map[String, Any]()
  val responseActor = context.actorOf(TellServiceActor.props())

  override def receive: Receive = {
    case SetRequest(id, msg) =>
      context.system.scheduler.scheduleOnce(3 seconds, responseActor, RequestMsg(id, msg))
    case ResponseMsg(id, response) =>
      log.info(s"response: $response")
      msgMap(id) = response
    case GetRequest(id: String) =>
      msgMap(id) match {
        case Some(resp) =>
          senderRef ! resp
        case None =>
          senderRef ! Failure(new Exception("ID not found: " + id))
      }
    case t =>
      log.warning("Unsupported message type: " + t.getClass)
  }
}

object TellClientActor {
  def props(): Props = Props[TellClientActor]
}
