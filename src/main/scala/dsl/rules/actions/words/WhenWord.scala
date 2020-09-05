package dsl.rules.actions.words

import dsl.nodes.RuleBook
import model.entities.runtime.GameState

case class WhenWord(condition: GameState => Boolean)(implicit ruleBook: RuleBook) {

  val allowed: AllowWord = new AllowWord(condition)

  val negated: NegateWord = new NegateWord(condition)

}
