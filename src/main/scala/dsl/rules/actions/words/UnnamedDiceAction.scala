package dsl.rules.actions.words

import dsl.nodes.RuleBook
import model.entities.runtime.GameState

case class UnnamedDiceAction(when: GameState => Boolean, allow: Boolean, diceNumber: Int, diceName: String, isMovement: Boolean)(implicit ruleBook: RuleBook) {
  def as(actionName: String): DiceAction = DiceAction(actionName, when, allow, diceNumber, diceName, isMovement)
}