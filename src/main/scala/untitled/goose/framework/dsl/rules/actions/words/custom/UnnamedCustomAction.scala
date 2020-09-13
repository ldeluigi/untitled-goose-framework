package untitled.goose.framework.dsl.rules.actions.words.custom

import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.GameState

case class UnnamedCustomAction(when: GameState => Boolean, customEvent: CustomEventInstance[GameState], allow: Boolean)(implicit ruleBook: RuleBook) {

  def as(name: String): NamedCustomAction = NamedCustomAction(name, when, customEvent, allow)
}
