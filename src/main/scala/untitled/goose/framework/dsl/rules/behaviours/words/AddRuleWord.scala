package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

case class AddRuleWord() {
  def ruleFor[T <: ConsumableGameEvent](node: AnyRef): Unit = ???
}
