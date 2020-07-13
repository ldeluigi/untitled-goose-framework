package model.actions

import model.MatchState

trait Action {
  def execute(gameState: MatchState) : MatchState
}
