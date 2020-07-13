package view

import model.MatchState
import model.entities.board.Board

trait BoardView {
  def updateMatchState(matchState: MatchState)
}
