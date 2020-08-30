package dsl.words

import dsl.nodes.RuleBook
import dsl.properties.PlayerOrderProperty

case class PlayersWord() {
  def start(on: OnWord): StartWord = new StartWord

  def have(order: PlayerOrderProperty)(implicit ruleBook: RuleBook): Unit =
    ruleBook.ruleSet.setPlayerOrderingType(order.value)
}
