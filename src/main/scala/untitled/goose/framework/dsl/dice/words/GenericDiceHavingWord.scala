package untitled.goose.framework.dsl.dice.words

import untitled.goose.framework.dsl.dice.nodes.DiceNode.GenericDiceNode
import untitled.goose.framework.dsl.nodes.RuleBook

case class GenericDiceHavingWord(name: String)(implicit ruleBook: RuleBook) {
  def having[T](sides: Seq[T]): Unit = ruleBook.diceCollection.add(GenericDiceNode(name, sides))
}
