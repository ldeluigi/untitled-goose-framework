package untitled.goose.framework.dsl.dice.words

import untitled.goose.framework.dsl.dice.nodes.DiceNode.GenericDiceNode
import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "dice having [property] ..." */
case class GenericDiceHavingWord(name: String)(implicit ruleBook: RuleBook) {

  /** Enables "dice having [sides]" */
  def having[T](sides: Seq[T]): Unit = ruleBook.diceCollection.add(GenericDiceNode(name, sides))
}
