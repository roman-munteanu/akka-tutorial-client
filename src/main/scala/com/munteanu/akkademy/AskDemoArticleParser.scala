package com.munteanu.akkademy

import akka.actor.{Actor, ActorSystem, Status}
import akka.actor.Actor.Receive
import akka.util.Timeout
import akka.pattern.ask
import com.munteanu.akkademy.messages.{GetRequest, SetRequest}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by highlander on 4/24/17.
  */
class AskDemoArticleParser(
  cacheActorPath: String,
  httpClientActorPath: String,
  htmlParserActorPath: String,
  implicit val timeout: Timeout) extends Actor {

  val system = ActorSystem("ask-demo-actor-system")
  val senderRef = sender()

  val cacheActor = system.actorSelection(cacheActorPath)
  val httpClientActor = system.actorSelection(httpClientActorPath)
  val htmlParserActor = system.actorSelection(htmlParserActorPath)

  override def receive: Receive = {
    case HtmlArticle(url: String) => {

      val cacheResult: Future[Any] = cacheActor ? GetRequest(url)

      val result = cacheResult.recoverWith {
        case _: Exception =>
          val futureHtmlContent: Future[Any] = httpClientActor ? url
          futureHtmlContent flatMap {
            case htmlContent: String =>
              htmlParserActor ? htmlContent
            case x =>
              Future.failed(new Exception("content not found"))
          }
      }

      result onComplete {
        case Success(content: String) =>
          senderRef ! content
        case Success(ArticleBody(body: String)) =>
          cacheActor ! SetRequest(url, body)
          senderRef ! body
        case Failure(ex: Exception) =>
          senderRef ! Status.Failure(ex)
        case _ =>
          println("Unknown message")
      }
    }
    case _ =>
      sender() ! Status.Failure(new ClassNotFoundException())
  }
}
