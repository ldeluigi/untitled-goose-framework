package dsl.rules.players

import dsl.UtilityWords.OnWord
import dsl.nodes.RuleBook

case class PlayersWord() {
  def start(on: OnWord): StartWord = new StartWord

  def have(order: PlayerOrderProperty)(implicit ruleBook: RuleBook): Unit =
    ruleBook.ruleSet.setPlayerOrderingType(order.value)
}
