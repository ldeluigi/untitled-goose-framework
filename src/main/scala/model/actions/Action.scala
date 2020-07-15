package model.actions

import model.MatchState

trait Action {
  def name: String

  def execute(gameState: MatchState): MatchState
}
