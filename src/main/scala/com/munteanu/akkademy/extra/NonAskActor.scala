package com.munteanu.akkademy.extra

import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive
import com.munteanu.akkademy.HtmlArticle
import com.munteanu.akkademy.messages.GetRequest

import scala.concurrent.duration._

/**
  * Created by romunteanu on 12/12/17.
  */
class NonAskActor(
   cacheActorPath: String,
   httpClientActorPath: String,
   htmlParserActorPath: String) extends Actor {

  import scala.concurrent.ExecutionContext.Implicits.global

  val senderRef = sender()
  val cacheActor = context.actorSelection(cacheActorPath)
  val httpClientActor = context.actorSelection(httpClientActorPath)
  val htmlParserActor = context.actorSelection(htmlParserActorPath)

  override def receive: Receive = {
    case msg @ HtmlArticle(uri) =>
      val extraActor = context.actorOf(ExtraActor.props(uri, senderRef, cacheActor, htmlParserActor))
      cacheActor.tell(GetRequest(uri), extraActor)
      httpClientActor.tell("test", extraActor)
      context.system.scheduler.scheduleOnce(5 seconds, extraActor, "timeout")
  }
}

object NonAskActor {
  def props(): Props = Props[NonAskActor]
}
