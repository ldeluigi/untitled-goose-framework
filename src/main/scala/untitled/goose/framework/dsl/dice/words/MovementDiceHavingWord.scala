package untitled.goose.framework.dsl.dice.words

import untitled.goose.framework.dsl.dice.nodes.DiceNode.MovementDiceNode
import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "movementDice having [property] ..." */
case class MovementDiceHavingWord(name: String)(implicit ruleBook: RuleBook) {

  /** Enables "movementDice having [sides]" */
  def having(sides: Seq[Int]): Unit = ruleBook.diceCollection.add(MovementDiceNode(name, sides))
}
