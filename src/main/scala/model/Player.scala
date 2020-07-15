package model

import engine.events.GameEventHandler

trait Player {
  def name: String

  def history: List[GameEvent] //TODO change
}

object Player {
  def apply(name: String): Player = PlayerImpl(name)
}

case class PlayerImpl(playerName: String) extends Player {

  override def name: String = playerName

  override def history: List[GameEvent] = ???
}
