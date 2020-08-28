package main

import java.awt.{Dimension, Toolkit}

import engine.core.EventSink
import engine.events.GameEvent
import engine.events.consumable.{DialogLaunchEvent, StepMovementEvent}
import engine.events.special.NoOpEvent
import javafx.scene.input.KeyCode
import model.actions.{Action, RollMovementDice}
import model.entities.Dice.MovementDice
import model.entities.board.{Board, Disposition, Position}
import model.entities.{DialogContent, Dice}
import model.game.MutableGameState
import model.rules.ActionRule
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.behaviours.{BehaviourRule, MovementWithDiceBehaviour, MultipleStepBehaviour}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.{GameData, TileIdentifier}
import scalafx.application.JFXApp
import scalafx.scene.paint.Color
import view.TileIdentifierImplicit._
import view.board.GraphicDescriptor
import view.playerselection.PlayerSelection

import scala.io.{BufferedSource, Source}

/**
 * Main application used to specify all game rules, actions, behaviours and graphical properties
 */
object Main extends JFXApp {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  //From DSL generation
  val totalTiles = 50
  val board: Board = Board(totalTiles, Disposition.snake(totalTiles))
  val movementDice: MovementDice = Dice.Factory.randomMovement((1 to 6).toSet, "six face")
  val testDialog: Action = new Action {
    override def name: String = "Launch Dialog!"

    override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
      sink.accept(DialogLaunchEvent(state.currentTurn, state.currentCycle, DialogContent(
        "Movement Bonus",
        "Make 10 step?",
        answers =
          "Yes" -> StepMovementEvent(10, state.currentPlayer, state.currentTurn, state.currentCycle),
        "No" -> NoOpEvent
      )))
    }
  }
  val actionRules: Set[ActionRule] = Set(AlwaysPermittedActionRule(1, RollMovementDice(movementDice), testDialog))
  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour())

  val priorityRuleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.orderedRandom,
    1 to 10,
    actionRules,
    behaviourRule,
    Seq(),
  )
  val ruleSet: RuleSet = priorityRuleSet

  val pathToOca = "oca.jpg"
  val oca: BufferedSource = Source.fromResource(pathToOca)

  // simulazione di mappa definita dall'utente
  val graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map(
    1 -> GraphicDescriptor(Color.Red)
  )
  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = new PlayerSelection(this, GameData(board, ruleSet), screenSize.width, screenSize.height, graphicMap)
    fullScreenExitHint = "Press esc to leave full screen mode"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
}
