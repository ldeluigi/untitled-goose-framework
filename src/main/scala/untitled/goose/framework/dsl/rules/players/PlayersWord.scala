package untitled.goose.framework.dsl.rules.players

import untitled.goose.framework.dsl.UtilityWords.{OnWord, PriorityWord}
import untitled.goose.framework.dsl.nodes.RuleBook

case class PlayersWord() {
  def start(on: OnWord): StartWord = new StartWord

  def have(order: PlayerOrderProperty)(implicit ruleBook: RuleBook): Unit =
    ruleBook.ruleSet.setPlayerOrderingType(order.value)

  def loseTurn(priority: PriorityWord): LoseTurnWord = LoseTurnWord()
}
