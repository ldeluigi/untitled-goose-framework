package main

import java.awt.{Dimension, Toolkit}

import engine.core.EventSink
import engine.events.GameEvent
import engine.events.consumable.{DialogLaunchEvent, StepMovementEvent}
import engine.events.special.NoOpEvent
import javafx.scene.input.KeyCode
import model.actions.{Action, RollMovementDice}
import model.entities.Dice.MovementDice
import model.entities.board.{Board, Disposition, Piece, Position}
import model.entities.{DialogContent, Dice}
import model.game.{Game, MutableGameState}
import model.rules.ActionRule
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.behaviours.{BehaviourRule, MovementWithDiceBehaviour, MultipleStepBehaviour}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.{Color, Player, TileIdentifier}
import scalafx.application.JFXApp
import view.ApplicationController
import view.board.GraphicDescriptor

/**
 * Example of a Goose Game without the use of a support DSL.
 * -- as of now, the use of the DSL itself is simulated --
 */
object TestMain extends JFXApp {

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
        "Yes" -> StepMovementEvent(10, state.currentPlayer, state.currentTurn, state.currentCycle),
        "No" -> NoOpEvent
      )))
    }
  }
  val actionRules: Set[ActionRule] = Set(AlwaysPermittedActionRule(1, RollMovementDice(movementDice), testDialog))
  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour())


  val priorityRuleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.randomOrder,
    1 to 10,
    actionRules,
    behaviourRule,
  )

  val ruleSet: RuleSet = priorityRuleSet


  //From a menu GUI that select and creates player and pieces on the press of a "Start game" button
  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  //List.range(1, 10).map(a => Player("P" + a) -> Piece()).toMap

  val currentMatch: Game = Game(board, players, ruleSet)

  val graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map()
  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    //fullScreen = true
    minWidth = 0.75 * screenSize.width
    minHeight = 0.75 * screenSize.height
    scene = ApplicationController(this, screenSize.width, screenSize.height, currentMatch, graphicMap)
    fullScreenExitHint = "Premi esc per uscire"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode.equals(KeyCode.F11)) {
      stage.setFullScreen(true)
    }
  )

}
