package dsl.words

import model.entities.runtime.GameState
import model.events.GameEvent

class AllowWord() {
  def apply(to: ToWord): AllowWord = new AllowWord()

  def trigger(event: GameState => GameEvent): UnnamedAction = ???

  def use(name: String): NamedAction = ???
}
