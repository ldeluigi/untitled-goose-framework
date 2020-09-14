package untitled.goose.framework.dsl.rules.players

import untitled.goose.framework.dsl.UtilityWords.OnWord
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.PriorityWord

case class PlayersWord() {
  def start(on: OnWord): StartWord = new StartWord

  def have(order: PlayerOrderProperty)(implicit ruleBook: RuleBook): Unit =
    ruleBook.ruleSet.setPlayerOrderingType(order.value)

  def loseTurn(priority: PriorityWord): LoseTurnWord = LoseTurnWord()
}
