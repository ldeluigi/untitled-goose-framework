package dsl.nodes

import model.entities.runtime.GameState
import model.events.GameEvent

sealed trait ActionRuleNode extends RuleBookNode

object ActionRuleNode {

  case class ActionRuleWithActionNode(name: String, when: GameState => Boolean, trigger: GameState => GameEvent, priority: Int, allow: Boolean)
    extends ActionRuleNode {
    override def check: Seq[String] = ???
  }

  case class ActionRuleWithRefNode(when: GameState => Boolean, priority: Int, allow: Boolean, refName: Set[String]) extends ActionRuleNode {
    override def check: Seq[String] = ???
  }

}
