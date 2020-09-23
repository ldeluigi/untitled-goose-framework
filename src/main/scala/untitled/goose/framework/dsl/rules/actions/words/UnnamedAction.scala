package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

/** Used for "[action] as [name]..." */
case class UnnamedAction(when: GameState => Boolean, trigger: GameState => GameEvent, allow: Boolean) {

  /** Enables "[action] as [name]..." */
  def as(name: String): NamedAction = NamedAction(name, when, trigger, allow)
}
