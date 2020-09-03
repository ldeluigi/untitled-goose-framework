package dsl.words

import model.events.GameEvent
import model.game.GameState

class AllowWord() {
  def apply(to: ToWord): AllowWord = new AllowWord()

  def trigger(event: GameState => GameEvent): UnnamedAction = ???

  def use(name: String): NamedAction = ???
}
