package untitled.goose.framework.dsl.rules.actions.words.dice

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.dice
import untitled.goose.framework.model.entities.runtime.GameState

case class UnnamedDiceAction(when: GameState => Boolean, allow: Boolean, diceNumber: Int, diceName: String, isMovement: Boolean)(implicit ruleBook: RuleBook) {
  def as(actionName: String): DiceAction = dice.DiceAction(actionName, when, allow, diceNumber, diceName, isMovement)
}
