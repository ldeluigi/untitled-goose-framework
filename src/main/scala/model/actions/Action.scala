package model.actions

import model.MatchState

trait Action {
  def name: String

  def execute(gameState: MatchState): MatchState

  override def equals(obj: Any): Boolean = obj match {
    case a : Action if a.name == this.name => true
    case _ => false
  }
}
