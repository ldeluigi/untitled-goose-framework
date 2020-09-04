package dsl.nodes

import model.actions.Action
import model.entities.runtime.GameState
import model.events.GameEvent
import model.rules.actionrules.{ActionAvailability, ActionRule}

sealed trait ActionRuleNode extends RuleBookNode {
  def generateActionRule(): ActionRule
}

object ActionRuleNode {

  case class ActionRuleWithActionNode
  (name: String, when: GameState => Boolean, trigger: GameState => GameEvent, priority: Int, allow: Boolean)
    extends ActionRuleNode {

    override def check: Seq[String] = Seq()

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = Set((allow, priority, generateAction()))
      ActionRule(availabilities, when)
    }

    def generateAction(): Action = Action(name, trigger)
  }

  case class ActionRuleWithRefNode
  (when: GameState => Boolean, priority: Int, allow: Boolean, refName: Set[String], definedActions: DefinedActions)
    extends ActionRuleNode {

    override def check: Seq[String] =
      refName.filter(!isActionDefined(_)).map("Action with name: " + _ + " was never defined").toSeq

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = refName.map(getAction).map(a => ActionAvailability(a, priority, allow))
      ActionRule(availabilities, when)
    }

    private def isActionDefined(name: String): Boolean = definedActions.isActionDefined(name)

    private def getAction(name: String): Action = definedActions.getAction(name)
  }

}
