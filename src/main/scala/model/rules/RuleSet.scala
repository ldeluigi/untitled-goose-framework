package model.rules

import model.{MatchState, Player}
import model.actions.Action

trait RuleSet {

  def first(players: Set[Player]): Player

  def actions(state: MatchState): Set[Action]

  def resolveAction(state: MatchState, action: Action): MatchState

}
