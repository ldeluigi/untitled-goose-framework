package model.rules

import model.{MatchState, Player}
import model.actions.Action
import model.entities.board.Position

trait RuleSet {

  def first(players: Set[Player]): Player

  def startPosition(): Position

  def actions(state: MatchState): Set[Action]

  def resolveAction(state: MatchState, action: Action): MatchState

}
