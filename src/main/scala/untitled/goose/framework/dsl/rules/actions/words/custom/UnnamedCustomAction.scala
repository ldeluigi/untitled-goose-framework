package untitled.goose.framework.dsl.rules.actions.words.custom

import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.GameState

/** Used for "action [property] ..." */
case class UnnamedCustomAction(when: GameState => Boolean, customEvent: CustomEventInstance[GameState])(implicit ruleBook: RuleBook) {

  /** Enables "... as [name] ..." */
  def as(name: String): NamedCustomAction = NamedCustomAction(name, when, customEvent)
}
