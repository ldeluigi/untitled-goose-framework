package dsl.nodes

import model.actions.Action
import model.entities.runtime.GameState
import model.events.GameEvent
import model.rules.actionrules.{ActionAvailability, ActionRule}

sealed trait ActionRuleNode extends RuleBookNode {
  def generateActionRule(): ActionRule
}

object ActionRuleNode {

  case class ActionRuleWithActionNode(name: String, when: GameState => Boolean, trigger: GameState => GameEvent, priority: Int, allow: Boolean)
    extends ActionRuleNode {
    override def check: Seq[String] = Seq()

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = Set((allow, priority, generateAction()))
      ActionRule(availabilities, when)
    }

    def generateAction(): Action = {
      val action = Action(name, trigger)
      registerAction(action)
      action
    }

    private def registerAction(action: Action): Unit = ???
  }

  case class ActionRuleWithRefNode(when: GameState => Boolean, priority: Int, allow: Boolean, refName: Set[String]) extends ActionRuleNode {
    override def check: Seq[String] =
      refName.filter(!isActionDefined(_)).map("Action with name: " + _ + " was never defined").toSeq

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = refName.map(getAction).map(a => ActionAvailability(a, priority, allow))
      ActionRule(availabilities, when)
    }

    private def isActionDefined(name: String): Boolean = ???

    private def getAction(name: String): Action = ???
  }

}
