package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.dsl.dice.nodes.DiceCollection
import untitled.goose.framework.dsl.events.nodes.EventCollection
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.actions.{Action, RollDice, RollMovementDice}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.rules.actionrules.{ActionAvailability, ActionRule}

object ActionRuleNode {

  sealed trait ActionRuleNode extends RuleBookNode {
    def generateActionRule(): ActionRule
  }

  sealed trait ActionGeneration {
    def generateAction(): Action
  }


  case class ActionRuleNodeImpl(name: String,
                                when: GameState => Boolean,
                                trigger: GameState => GameEvent,
                                priority: Int,
                                allow: Boolean)
    extends ActionRuleNode with ActionGeneration {

    override def check: Seq[String] = Seq()

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = Set((allow, priority, generateAction()))
      ActionRule(availabilities, when)
    }

    override def generateAction(): Action = Action(name, trigger)
  }

  case class ActionRuleWithRefNode(when: GameState => Boolean,
                                   priority: Int,
                                   allow: Boolean,
                                   refName: Set[String],
                                   definedActions: ActionCollection)
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

  //TODO Implement this
  case class CustomEventActionNode(actionName: String,
                                   when: GameState => Boolean,
                                   customEvent: CustomEventInstance,
                                   priority: Int,
                                   allow: Boolean,
                                   definedEvents: EventCollection)
    extends ActionRuleNode with ActionGeneration {

    override def generateActionRule(): ActionRule = ???

    override def generateAction(): Action = ???

    override def check: Seq[String] = ???
  }

}
