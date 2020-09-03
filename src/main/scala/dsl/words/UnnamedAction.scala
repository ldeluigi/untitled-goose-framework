package dsl.words

import model.entities.runtime.GameState
import model.events.GameEvent

case class UnnamedAction(when: GameState => Boolean, trigger: GameState => GameEvent, allow: Boolean) {

  def as(name: String): NamedAction = NamedAction(name, when, trigger, allow)
}
