package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.GameState

case class WhenWord(condition: GameState => Boolean)(implicit ruleBook: RuleBook) {

  val allowed: AllowWord = new AllowWord(condition)

  val negated: NegateWord = new NegateWord(condition)

}
