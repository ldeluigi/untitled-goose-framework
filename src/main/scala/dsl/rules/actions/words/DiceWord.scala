package dsl.rules.actions.words

import dsl.nodes.RuleBook
import model.entities.runtime.GameState


case class DiceWord(when: GameState => Boolean, allow: Boolean, diceNumber: Int)(implicit ruleBook: RuleBook) {

  def dice(diceName: String): UnnamedDiceAction = UnnamedDiceAction(when, allow, diceNumber, diceName, isMovement = false)

  def movementDice(diceName: String): UnnamedDiceAction = UnnamedDiceAction(when, allow, diceNumber, diceName, isMovement = true)
}

