package untitled.goose.framework.main

import java.awt.{Dimension, Toolkit}

import untitled.goose.framework.model.GraphicDescriptor
import untitled.goose.framework.model.actions.{Action, RollMovementDice}
import untitled.goose.framework.model.entities.Dice.MovementDice
import untitled.goose.framework.model.entities.definitions.{PlayerOrderingType, _}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.entities.{DialogContent, Dice}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.{DialogLaunchEvent, StepMovementEvent}
import untitled.goose.framework.model.events.special.NoOpEvent
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import untitled.goose.framework.model.rules.behaviours.{BehaviourRule, MovementWithDiceBehaviour, MultipleStepBehaviour}
import untitled.goose.framework.view.scalafx.TileIdentifierImplicit._
import untitled.goose.framework.view.scalafx.View

/**
 * Main application used to specify all runtime rules, actions, behaviours and graphical properties
 */
object Main extends App {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  //From DSL generation
  val totalTiles = 50
  val board: BoardDefinition = BoardDefinition("Test Main", totalTiles, Disposition.snake(totalTiles))
  val movementDice: MovementDice = Dice.Factory.randomMovement(1 to 6, "six face")
  val testDialog: Action = new Action {
    override def name: String = "Launch Dialog!"

    override def trigger(state: GameState): GameEvent = {
      DialogLaunchEvent(state.currentTurn, state.currentCycle, DialogContent(
        "Movement Bonus",
        "Make 10 step?",
        Options = "Yes" -> StepMovementEvent(10, state.currentPlayer, state.currentTurn, state.currentCycle),
        "No" -> NoOpEvent
      ))
    }
  }
  val actionRules: Set[ActionRule] = Set(AlwaysPermittedActionRule(1, RollMovementDice(movementDice), testDialog))
  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour())

  val gameData: GameDefinition = GameDefinitionBuilder()
    .board(board)
    .playerOrderingType(PlayerOrderingType.Fixed)
    .playersRange(1 to 10)
    .actionRules(actionRules)
    .behaviourRules(behaviourRule)
    .cleanupRules(Seq())
    .build()

  // simulazione di mappa definita dall'utente
  val graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map(
    1 -> GraphicDescriptor("pozzo.png")
  )

  new View(gameData, graphicMap).main(args)

}
