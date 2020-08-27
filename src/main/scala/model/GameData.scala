package model

import model.entities.board.Board
import model.rules.ruleset.RuleSet

trait GameData {
  def board: Board

  def ruleSet: RuleSet
}

object GameData {
  //TODO probably change this
  def apply(b: Board, r: RuleSet): GameData = new GameData() {
    override val board: Board = b

    override val ruleSet: RuleSet = r
  }
}