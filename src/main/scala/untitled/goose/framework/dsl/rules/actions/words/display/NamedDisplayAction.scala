package untitled.goose.framework.dsl.rules.actions.words.display

import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.DisplayActionRuleNode
import untitled.goose.framework.model.entities.runtime.GameState

case class NamedDisplayAction(name: String, when: GameState => Boolean, title: String, text: String, options: Seq[(String, CustomEventInstance[GameState])], allow: Boolean) {

  def priority(priority: Int): DisplayActionRuleNode = DisplayActionRuleNode(name, when, title, text, options, priority, allow)
}
