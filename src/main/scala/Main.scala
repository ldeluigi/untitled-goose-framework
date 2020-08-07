import java.awt.{Dimension, Toolkit}

import engine.`match`.Match
import engine.events.DialogLaunchEvent
import engine.events.core.EventSink
import engine.events.root.GameEvent
import javafx.scene.input.KeyCode
import model.{Color, MatchState, Player}
import model.actions.{Action, RollDice}
import model.entities.{DialogContent, Dice}
import model.entities.board.{Board, Disposition, Piece, Position}
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.behaviours.{MovementWithDiceBehaviour, MultipleStepBehaviour}
import model.rules.{ActionRule, BehaviourRule}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import scalafx.application.JFXApp
import view.ApplicationController
import view.intro.IntroMenu

object Main extends JFXApp {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  //From DSL generation
  val totalTiles = 50
  val board: Board = Board(totalTiles, Disposition.snake(totalTiles))
  val movementDice: Dice[Int] = Dice[Int]((1 to 6).toSet, "six face")
  val testDialog: Action = new Action {
    override def name: String = "Launch Dialog!"

    override def execute(sink: EventSink[GameEvent], state: MatchState): Unit = {
      sink.accept(DialogLaunchEvent(state.currentTurn, s => DialogContent.testDialog(s)))
    }
  }
  val actionRules: Set[ActionRule] = Set(AlwaysPermittedActionRule(1, RollDice(movementDice), testDialog))
  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour())


  val priorityRuleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.orderedRandom,
    actionRules,
    behaviourRule
  )

  val ruleSet: RuleSet = priorityRuleSet

  val introMenu = new IntroMenu(board, ruleSet, screenSize.width, screenSize.height)

  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = introMenu
    fullScreenExitHint = "Premi esc per uscire"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
  //TODO fix this stage.setOnCloseRequest(_ => appView.close())
}