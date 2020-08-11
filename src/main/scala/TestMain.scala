import java.awt.{Dimension, Toolkit}

import engine.`match`.Match
import engine.core.EventSink
import engine.events.{DialogLaunchEvent, StepMovementEvent}
import engine.events.root.{GameEvent, NoOpEvent}
import javafx.scene.input.KeyCode
import model.actions.{Action, RollMovementDice}
import model.entities.Dice.MovementDice
import model.entities.board.{Board, Disposition, Piece, Position}
import model.entities.{DialogContent, Dice}
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.behaviours.{MovementWithDiceBehaviour, MultipleStepBehaviour, TurnEndEventBehaviour}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.rules.{ActionRule, BehaviourRule}
import model.{Color, MutableMatchState, Player}
import scalafx.application.JFXApp
import view.ApplicationController
import view.playerselection.PlayerSelection

object TestMain extends JFXApp {


  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  //From DSL generation
  val totalTiles = 50
  val board: Board = Board(totalTiles, Disposition.snake(totalTiles))
  val movementDice: MovementDice = Dice.Factory.randomMovement((1 to 6).toSet, "six face")
  val testDialog: Action = new Action {
    override def name: String = "Launch Dialog!"

    override def execute(sink: EventSink[GameEvent], state: MutableMatchState): Unit = {
      sink.accept(DialogLaunchEvent(state.currentTurn, s => DialogContent(
        "Movement Bonus",
        "Make 10 step?",
        "Yes" -> StepMovementEvent(10, s.currentPlayer, s.currentTurn),
        "No" -> NoOpEvent
      )))
    }
  }
  val actionRules: Set[ActionRule] = Set(AlwaysPermittedActionRule(1, RollMovementDice(movementDice), testDialog))
  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour(), TurnEndEventBehaviour())


  val priorityRuleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.orderedRandom,
    actionRules,
    behaviourRule
  )

  val ruleSet: RuleSet = priorityRuleSet


  //From a menu GUI that select and creates player and pieces on the press of a "Start game" button
  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  //List.range(1, 10).map(a => Player("P" + a) -> Piece()).toMap

  val currentMatch: Match = Match(board, players, ruleSet)
  val appView: ApplicationController = ApplicationController(screenSize.width, screenSize.height, currentMatch)

  val playerSelection = new PlayerSelection(stage, board, ruleSet, screenSize.width, screenSize.height)

  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    //fullScreen = true
    minWidth = 0.75 * screenSize.width
    minHeight = 0.75 * screenSize.height
    scene = appView
    fullScreenExitHint = "Premi esc per uscire"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode.equals(KeyCode.F11)) {
      stage.setFullScreen(true)
    }
  )

  stage.setOnCloseRequest(_ => appView.close())
}
