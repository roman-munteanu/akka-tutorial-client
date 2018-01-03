package com.munteanu.akkademy.lobby

import akka.actor.ActorRef

case class UserRef(actor: ActorRef, username: String)
