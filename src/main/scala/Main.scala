import java.awt.{Dimension, Toolkit}

import engine.`match`.Match
import javafx.scene.input.KeyCode
import model.{Color, Player}
import model.actions.RollDice
import model.entities.Dice
import model.entities.board.{Board, Disposition, Piece, Position}
import model.rules.actionrules.AlwaysPermittedActionRule
import model.rules.behaviours.{MovementWithDiceBehaviour, MultipleStepBehaviour}
import model.rules.{ActionRule, BehaviourRule}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import scalafx.application.JFXApp
import view.ApplicationController
import view.intro.IntroMenu

object Main extends JFXApp {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  //From DSL generation
  val totalTiles = 24
  val board: Board = Board(totalTiles, Disposition.spiral(totalTiles))
  val movementDice: Dice[Int] = Dice[Int]((1 to 6).toSet, "six face")
  val actionRules: Set[ActionRule] = Set(AlwaysPermittedActionRule(RollDice(movementDice)))
  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour())


  val priorityRuleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.orderedRandom,
    actionRules,
    behaviourRule
  )

  val ruleSet: RuleSet = priorityRuleSet

  new IntroMenu(board, ruleSet)

  //From a menu GUI that select and creates player and pieces on the press of a "Start game" button
  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))

  val currentMatch: Match = Match(board, players, ruleSet)
  val appView: ApplicationController = ApplicationController(screenSize.width, screenSize.height, currentMatch)

  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = appView
    fullScreenExitHint = "Premi esc per uscire"
  }
  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
  stage.setOnCloseRequest(_ => appView.close())
}