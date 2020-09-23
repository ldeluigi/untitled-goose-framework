package untitled.goose.framework.dsl.rules.actions.words.dice

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.GameState

/** Used for actions that roll dices. */
case class ActionDiceWord(when: GameState => Boolean, diceNumber: Int)(implicit ruleBook: RuleBook) {

  /** Enables "[verb] dice [name] ..." action */
  def dice(diceName: String): UnnamedDiceAction = UnnamedDiceAction(when, diceNumber, diceName, isMovement = false)

  /** Enables "[verb] movementDice [name] ..." action */
  def movementDice(diceName: String): UnnamedDiceAction = UnnamedDiceAction(when, diceNumber, diceName, isMovement = true)
}
