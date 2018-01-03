package com.munteanu.akkademy.lobby

import akka.actor.Actor
import akka.event.Logging

class ChatroomActor extends Actor {

  val log = Logging(context.system, this)
  var joinerUsers: Seq[UserRef] = Seq.empty
  var chatHistory: Seq[PostToChatroom] = Seq.empty

  override def receive: Receive = {
    case JoinChatRoom(userRef) =>
      joinerUsers = joinerUsers :+ userRef
    case _ =>
      log.info("Unknown message")
  }
}
