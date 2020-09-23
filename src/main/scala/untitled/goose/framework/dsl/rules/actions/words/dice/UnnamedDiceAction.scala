package untitled.goose.framework.dsl.rules.actions.words.dice

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.dice
import untitled.goose.framework.model.entities.runtime.GameState

/** Used for "... [dice action] as [name] ..." */
case class UnnamedDiceAction(when: GameState => Boolean, diceNumber: Int, diceName: String, isMovement: Boolean)(implicit ruleBook: RuleBook) {

  /** Enables "... [dice action] as [name] ..." */
  def as(actionName: String): DiceAction = dice.DiceAction(actionName, when, diceNumber, diceName, isMovement)
}
