package com.munteanu.akkademy.scaling

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object ScalingMain {
  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global

    val articles = List(
      "<DOCTYPE html><html><body><p>text 1</p></body></html>",
      "<DOCTYPE html><html><body><h1>text 2</h1></body></html>"
    )

    val futures: List[Future[String]] = articles.map(article => Future { ArticleParser(article) })
    val articleFutures: Future[List[String]] = Future.sequence(futures)

    val ls = Await.result(articleFutures, 5 seconds)
    println(ls.mkString(","))
  }
}
