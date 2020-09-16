package untitled.goose.framework.dsl.rules.actions.words.display

import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.model.entities.runtime.GameState

case class UnnamedDisplayAction(when: GameState => Boolean, title: String, text: String, allow: Boolean, options: (String, CustomEventInstance[GameState])*) {

  def as(name: String): NamedDisplayAction = NamedDisplayAction(name, when, title, text, options = options, allow = true)
}
