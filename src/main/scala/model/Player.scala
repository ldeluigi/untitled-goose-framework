package model

import engine.events.GameEventHandler

trait Player {
  def name: String

  def history: List[GameEvent] //TODO change
}

object Player {

  private class PlayerImpl(playerName: String) extends Player {

    override def name: String = playerName

    override def history: List[GameEvent] = ???
  }

  def apply(name: String): Player = new PlayerImpl(name)
}
