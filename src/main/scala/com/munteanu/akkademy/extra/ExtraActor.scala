package com.munteanu.akkademy.extra

import akka.actor.Actor.Receive
import akka.actor.Status.Failure
import akka.actor.{ActorSelection, ActorRef, Actor, Props}
import akka.event.Logging
import com.munteanu.akkademy.ArticleBody
import com.munteanu.akkademy.messages.SetRequest

/**
  * Created by romunteanu on 12/12/17.
  */
class ExtraActor(
  uri: String,
  senderRef: ActorRef,
  cacheActor: ActorSelection,
  articleParserActor: ActorSelection) extends Actor {

  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "timeout" =>
      senderRef ! Failure(new Exception("Timeout occurred!"))
      context.stop(self)
//      self ! PoisonPill

//    case HttpResponse(body) =>
//      articleParserActor ! body

    case ArticleBody(body) =>
      cacheActor ! SetRequest(uri, body)
      senderRef ! body
      context.stop(self)
    case body: String =>
      senderRef ! body
      context.stop(self)
    case t =>
      log.warning("Unsupported message type: " + t.getClass)
  }
}

object ExtraActor {
  def props(uri: String, senderRef: ActorRef, cacheActor: ActorSelection, articleParserActor: ActorSelection): Props =
    Props(new ExtraActor(uri, senderRef, cacheActor, articleParserActor))
}