package dsl.rules.actions.nodes

import dsl.dice.nodes.DiceCollection
import dsl.nodes.RuleBookNode
import model.actions.{Action, RollDice, RollMovementDice}
import model.entities.runtime.GameState
import model.events.GameEvent
import model.rules.actionrules.{ActionAvailability, ActionRule}

object ActionRuleNode {

  sealed trait ActionRuleNode extends RuleBookNode {
    def generateActionRule(): ActionRule
  }

  sealed trait ActionGeneration {
    def generateAction(): Action
  }


  case class ActionRuleNodeImpl
  (name: String, when: GameState => Boolean, trigger: GameState => GameEvent, priority: Int, allow: Boolean)
    extends ActionRuleNode with ActionGeneration {

    override def check: Seq[String] = Seq()

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = Set((allow, priority, generateAction()))
      ActionRule(availabilities, when)
    }

    override def generateAction(): Action = Action(name, trigger)
  }

  case class ActionRuleWithRefNode
  (when: GameState => Boolean, priority: Int, allow: Boolean, refName: Set[String], definedActions: ActionCollection)
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

  case class DiceActionNode(actionName: String,
                            when: GameState => Boolean,
                            priority: Int,
                            allow: Boolean,
                            diceNumber: Int,
                            diceName: String,
                            isMovement: Boolean,
                            definedDice: DiceCollection)
    extends ActionRuleNode with ActionGeneration {

    override def check: Seq[String] = if (definedDice.isDiceDefined(diceName)) {
      if (isMovement) {
        if (!definedDice.isMovementDice(diceName)) {
          Seq("Dice " + diceName + " is not defined as a MovementDice")
        }
      } else {
        if (definedDice.isMovementDice(diceName))
          Seq("Warning: using " + diceName + " as a non-movement dice may cause an unexpected behaviour")
      }
      Seq()
    } else Seq("Dice " + diceName + " was never defined")

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = Set((allow, priority, generateAction()))
      ActionRule(availabilities, when)
    }

    override def generateAction(): Action = if (isMovement)
      RollMovementDice(actionName, definedDice.getMovementDice(diceName), diceNumber)
    else
      RollDice(actionName, definedDice.getDice(diceName), diceNumber)
  }

}
