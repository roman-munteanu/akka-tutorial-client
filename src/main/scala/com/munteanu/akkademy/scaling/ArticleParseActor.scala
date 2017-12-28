package com.munteanu.akkademy.scaling

import akka.actor.Actor
import akka.event.Logging

class ArticleParseActor extends Actor {

  val log = Logging(context.system, this)
  val senderRef = sender()

  override def receive: Receive = {
    case ParseArticle(html) =>
      val body = ArticleParser(html)
      log.info(s"ArticleParseActor - body: $body")
      senderRef ! body
  }
}
