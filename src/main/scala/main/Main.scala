package main

import java.awt.{Dimension, Toolkit}

import engine.core.EventSink
import engine.events.root.{GameEvent, NoOpEvent}
import engine.events.{DialogLaunchEvent, StepMovementEvent}
import javafx.scene.input.KeyCode
import scalafx.scene.paint.Color
import model.TileIdentifier
import model.TileIdentifier.{Group}
import model.actions.{Action, RollMovementDice}
import model.entities.Dice.MovementDice
import model.entities.board.{Board, Disposition, Position}
import model.entities.{DialogContent, Dice}
import model.game.MutableGameState
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.behaviours.{MovementWithDiceBehaviour, MultipleStepBehaviour}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.rules.{ActionRule, BehaviourRule}
import scalafx.application.JFXApp
import view.board.GraphicDescriptor
import view.playerselection.PlayerSelection

import scala.io.{BufferedSource, Source}

object Main extends JFXApp {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  //From DSL generation
  val totalTiles = 50
  val board: Board = Board(totalTiles, Disposition.snake(totalTiles))
  val movementDice: MovementDice = Dice.Factory.randomMovement((1 to 6).toSet, "six face")
  val testDialog: Action = new Action {
    override def name: String = "Launch Dialog!"

    override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
      sink.accept(DialogLaunchEvent(state.currentTurn, s => DialogContent(
        "Movement Bonus",
        "Make 10 step?",
        "Yes" -> StepMovementEvent(10, s.currentPlayer, s.currentTurn),
        "No" -> NoOpEvent
      )))
    }
  }
  val actionRules: Set[ActionRule] = Set(AlwaysPermittedActionRule(1, RollMovementDice(movementDice), testDialog))
  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour())

  val priorityRuleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.orderedRandom,
    actionRules,
    behaviourRule
  )
  val ruleSet: RuleSet = priorityRuleSet

  val oca : BufferedSource = Source.fromResource("oca.jpg")
  println("Prova visualizza path oca!!! --> " + oca)

  import view.TileIdentifierImplicit._
  val tupla: (TileIdentifier, GraphicDescriptor) = "oca" -> GraphicDescriptor("oca.jpg")

  def graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map(
    "oca" -> GraphicDescriptor("oca.jpg"),
    15 -> GraphicDescriptor("pozzo.jpg"),
    Group("verde") -> GraphicDescriptor(Color.Green)
  )

  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = new PlayerSelection(this, board, ruleSet, screenSize.width, screenSize.height)
    fullScreenExitHint = "Premi esc per uscire"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
}
