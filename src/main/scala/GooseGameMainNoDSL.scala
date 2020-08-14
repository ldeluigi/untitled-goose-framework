import java.awt.{Dimension, Toolkit}

import engine.`match`.Match
import engine.`match`.MatchStateExtensions._
import engine.events.{MovementDiceRollEvent, StopOnTileEvent, TeleportEvent, TurnEndedEvent}
import javafx.scene.input.KeyCode
import model.actions.RollMovementDice
import model.entities.Dice
import model.entities.Dice.MovementDice
import model.entities.board._
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.behaviours.{MovementWithDiceBehaviour, MultipleStepBehaviour, TurnEndEventBehaviour, VictoryBehaviour}
import model.rules.operations.Operation
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.rules.{ActionRule, BehaviourRule}
import model.{Color, MatchState, Player, Tile}
import scalafx.application.JFXApp
import view.ApplicationController //IMPORTANT
object GooseGameMainNoDSL extends JFXApp {


  //You will need
  val totalTiles = 63

  //name and groups of tiles:
  val gooseTileGroup = "GooseTile"
  val theBridge = "The Bridge"
  val theWell = "The Well"
  val theInn = "The Inn"
  val theLabyrinth = "The Labyrinth"
  val thePrison = "The Prison"
  val theDeath = "The Death"


  val tileSet: Set[TileDefinition] = ???
  val board: Board = Board("Original Goose Game", tileSet, Disposition.spiral(totalTiles))

  //2 dices
  val movementDice: MovementDice = Dice.Factory.randomMovement((1 to 6).toSet, "six sided dice")

  //To Win
  //TODO bounce back on winning tile
  val winningBehaviour: VictoryBehaviour = VictoryBehaviour()

  //To Play:

  //Each turn one player can:
  //Roll the dice and move the piece one square for the number resulted from the sum of the dice result.
  val rollDiceActionRule: AlwaysPermittedActionRule = AlwaysPermittedActionRule(1, RollMovementDice(movementDice, 2))

  //If you throw a 3 on your first turn you can move straight to square 26.
  val teleportTo26OnFirstTurn: BehaviourRule = (state: MatchState) => {
    if (state.getTile(26).isDefined &&
      state.currentPlayer.history.exists(!_.isInstanceOf[TurnEndedEvent])) { //is first turn
      val teleportTo: Tile = state.getTile(26).get
      state.currentPlayer.history
        .filter(_.turn == state.currentTurn)
        .find(_.isInstanceOf[MovementDiceRollEvent])
        .map(_.asInstanceOf[MovementDiceRollEvent])
        .filter(_.res.contains(3))
        .map(_ => Seq(Operation.trigger(s => Some(TeleportEvent(teleportTo, state.currentPlayer, state.currentTurn)))))
        .getOrElse(Seq())
    }
    else Seq()
  }

  val stopOnAGooseTile: BehaviourRule = (state: MatchState) => {
    state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .filter(_.source.belongsTo(gooseTileGroup))
    ???
  }


  val behaviourRule: Seq[BehaviourRule] = Seq(MultipleStepBehaviour(), MovementWithDiceBehaviour(), TurnEndEventBehaviour())
  val actionRules: Set[ActionRule] = Set()

  val ruleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.orderedRandom,
    actionRules,
    behaviourRule
  )

  //From a menu GUI that select and creates player and pieces on the press of a "Start game" button

  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  //List.range(1, 10).map(a => Player("P" + a) -> Piece()).toMap

  val currentMatch: Match = Match(board, players, ruleSet)

  //View launch
  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    //fullScreen = true
    minWidth = 0.75 * screenSize.width
    minHeight = 0.75 * screenSize.height
    scene = ApplicationController(this, screenSize.width, screenSize.height, currentMatch)
    fullScreenExitHint = "Premi esc per uscire"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode.equals(KeyCode.F11)) {
      stage.setFullScreen(true)
    }
  )

}
