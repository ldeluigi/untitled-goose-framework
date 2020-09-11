package untitled.goose.framework.main

import java.awt.{Dimension, Toolkit}

import javafx.scene.input.KeyCode
import scalafx.application.JFXApp
import scalafx.scene.image.Image
import untitled.goose.framework.model.actions.{Action, RollMovementDice}
import untitled.goose.framework.model.entities.Dice.MovementDice
import untitled.goose.framework.model.entities.definitions._
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.entities.{DialogContent, Dice}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.{DialogLaunchEvent, StepMovementEvent}
import untitled.goose.framework.model.events.special.NoOpEvent
import untitled.goose.framework.model.rules.actionrules.ActionRule
import untitled.goose.framework.model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import untitled.goose.framework.model.rules.behaviours.{BehaviourRule, MovementWithDiceBehaviour, MultipleStepBehaviour}
import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType
import untitled.goose.framework.view.scalafx.TileIdentifierImplicit._
import untitled.goose.framework.view.scalafx.board.GraphicDescriptor
import untitled.goose.framework.view.scalafx.playerselection.IntroMenu

/**
 * Main application used to specify all runtime rules, actions, behaviours and graphical properties
 */
object Main extends JFXApp {

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
    .build

  // simulazione di mappa definita dall'utente
  val graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map(
    1 -> GraphicDescriptor("pozzo.png")
  )
  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = new IntroMenu(this, gameData, board.name, screenSize.width, screenSize.height, graphicMap)
    fullScreenExitHint = "Press esc to leave full screen mode"
  }

  stage.getIcons.add(new Image("GooseLogo.png"))

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
}
