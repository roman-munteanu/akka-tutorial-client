package com.munteanu.akkademy.scaling

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinGroup

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object ScalingMain {
  def main(args: Array[String]): Unit = {

//    import scala.concurrent.ExecutionContext.Implicits.global

    val articles = List(
      "<!DOCTYPE html><html><body><p>text 1</p></body></html>",
      "<!DOCTYPE html><html><body><h1>text 2</h1></body></html>",
      "<!DOCTYPE html><html><body><p>text 3</p></body></html>",
      "<!DOCTYPE html><html><body><p>text 4</p></body></html>",
      "<!DOCTYPE html><html><body><p>text 5</p></body></html>",
      "<!DOCTYPE html><html><body><p>text 6</p></body></html>",
      "<!DOCTYPE html><html><body><p>text 7</p></body></html>",
      "<!DOCTYPE html><html><body><p>text 8</p></body></html>"
    )

//    val futures: List[Future[String]] = articles.map(article => Future { ArticleParser(article) })
//    val articleFutures: Future[List[String]] = Future.sequence(futures)
//
//    val ls = Await.result(articleFutures, 5 seconds)
//    println(ls.mkString(","))

    val system = ActorSystem("article-parsing-actor-system")

    val actors: List[ActorRef] = (0 to 7).map { x =>
      system.actorOf(Props(classOf[ArticleParseActor]).withDispatcher("akka.actor.article-parsing-dispatcher"))
    }.toList

    val workerRouter = system.actorOf(RoundRobinGroup(actors.map(x => x.path.toStringWithoutAddress)).props, "workerRouter")

    articles.foreach { html => workerRouter ! ParseArticle(html) }

  }
}
