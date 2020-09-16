package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.dsl.dice.nodes.DiceCollection
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.dsl.rules.actions.words.custom.ActionCustomEventInstance
import untitled.goose.framework.model.actions.{Action, RollDice, RollMovementDice}
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.DialogLaunchEvent
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
      refName.filter(!isActionDefined(_)).map("Action with name: \"" + _ + "\" was never defined").toSeq

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
                            diceCollection: DiceCollection)
    extends ActionRuleNode with ActionGeneration {

    override def check: Seq[String] = if (diceCollection.isDiceDefined(diceName)) {
      if (isMovement) {
        if (!diceCollection.isMovementDice(diceName)) {
          Seq("Dice \"" + diceName + "\" declared as a non-movement dice but used for movement")
        } else Seq()
      } else {
        if (diceCollection.isMovementDice(diceName)) {
          Seq("Dice \"" + diceName + "\" declared as a movement dice but not used for movement")
        } else Seq()
      }
    } else Seq("Dice \"" + diceName + "\" was never defined")

    override def generateActionRule(): ActionRule = {
      val availabilities: Set[ActionAvailability] = Set((allow, priority, generateAction()))
      ActionRule(availabilities, when)
    }

    override def generateAction(): Action = if (isMovement)
      RollMovementDice(actionName, diceCollection.getMovementDice(diceName), diceNumber)
    else
      RollDice(actionName, diceCollection.getDice(diceName), diceNumber)
  }

  case class CustomEventActionNode(actionName: String,
                                   when: GameState => Boolean,
                                   customEvent: CustomEventInstance[GameState],
                                   priority: Int,
                                   allow: Boolean)
    extends ActionRuleNode with ActionGeneration {

    override def generateActionRule(): ActionRule =
      ActionRule(Set(ActionAvailability(generateAction(), priority, allow)), when)

    override def generateAction(): Action = {
      Action(actionName, customEvent.generateEvent)
    }

    override def check: Seq[String] = customEvent.check
  }

  case class DisplayActionRuleNode(name: String,
                                   when: GameState => Boolean,
                                   title: String,
                                   text: String,
                                   options: Seq[(String, ActionCustomEventInstance)],
                                   priority: Int,
                                   allow: Boolean)
    extends ActionRuleNode with ActionGeneration {

    override def generateActionRule(): ActionRule = ActionRule(Set(ActionAvailability(generateAction(), priority, allow)), when)


    override def generateAction(): Action =
      Action(name, s => DialogLaunchEvent(s.currentTurn, s.currentCycle, DialogContent(title, text, options.map(o => (o._1, o._2.generateEvent(s))): _*)))


    override def check: Seq[String] = options.flatMap(_._2.check)
  }

}
