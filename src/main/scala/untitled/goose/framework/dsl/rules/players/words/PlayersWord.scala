package untitled.goose.framework.dsl.rules.players.words

import untitled.goose.framework.dsl.UtilityWords.{OnWord, PriorityWord}
import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "Players ..." */
case class PlayersWord() {

  /** Enables "players start on ..." */
  def start(on: OnWord): StartWord = new StartWord

  /** Enables "players have [ordering] ..." */
  def have(order: PlayerOrderProperty)(implicit ruleBook: RuleBook): Unit =
    ruleBook.ruleSet.setPlayerOrderingType(order.value)

  /** Enables "players loseTurn priority ..." */
  def loseTurn(priority: PriorityWord): LoseTurnWord = LoseTurnWord()
}
