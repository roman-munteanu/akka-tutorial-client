package com.munteanu.akkademy.lobby

import akka.actor.{ActorSystem, Props}
import akka.testkit.TestActorRef
import org.scalatest.{FunSpec, Matchers}

class ChatroomActorSpec extends FunSpec with Matchers {

  val system = ActorSystem()

  describe("Given a Chatroom has no users") {
    val ref: TestActorRef[ChatroomActor] = TestActorRef.create(system, Props(classOf[ChatroomActor]), "testA")
    val chatroomActor: ChatroomActor = ref.underlyingActor
    chatroomActor.joinerUsers.size should equal(0)
    it("when it receives a request from a user to join the chatroom") {
      val userRef: UserRef = UserRef(system.deadLetters, "test_username")
      ref ! JoinChatRoom(userRef)
      chatroomActor.joinerUsers.head should equal(userRef)
    }
  }
}
