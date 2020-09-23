package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.UtilityWords.ToWord
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions
import untitled.goose.framework.model.entities.runtime.GameState

/** Used to negate actions. */
class NegateWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): NegateWord = this

  /** Negates the given action. */
  def use(refName: String): RefAction = actions.words.RefAction(when, allow = false, Set(refName))
}
