package com.munteanu.akkademy.lobby

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.{FunSpecLike, Matchers}

class ChatroomSpec(_system: ActorSystem)
  extends TestKit(_system)
    with ImplicitSender
    with Matchers
    with FunSpecLike {

  import scala.concurrent.duration._

  describe("Given a chatroom has a history") {
    val ref = TestActorRef.create(system, Props(classOf[ChatroomActor]))
    val chatroomActor: ChatroomActor = ref.underlyingActor
    val username = "test_username"
    val msg = PostToChatroom("test_line", username)
    chatroomActor.chatHistory = msg +: chatroomActor.chatHistory
    describe("When a user joins the chatroom") {
      val userRef = UserRef(system.deadLetters, username)
      ref ! JoinChatRoom(userRef)
      it("the user should receive the history") {
        expectMsg(1 second, List(msg))
      }
    }
  }
}
