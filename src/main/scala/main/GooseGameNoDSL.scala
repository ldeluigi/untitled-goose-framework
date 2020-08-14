package main

import java.awt.{Dimension, Toolkit}

import engine.events._
import javafx.scene.input.KeyCode
import model.`match`.{Match, MatchState}
import model.actions.RollMovementDice
import model.entities.Dice
import model.entities.Dice.MovementDice
import model.entities.board._
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.behaviours.{MovementWithDiceBehaviour, MultipleStepBehaviour, TurnEndEventBehaviour, VictoryBehaviour}
import model.rules.operations.Operation
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.rules.{ActionRule, BehaviourRule}
import model.{Color, Player, Tile}
import scalafx.application.JFXApp
import view.ApplicationController


object GooseGameNoDSL extends JFXApp {

  import model.`match`.MatchStateExtensions._

  //You will need:
  val totalTiles = 63

  //name and groups of tiles:
  val gooseTileGroup = "GooseTile"
  val theBridge = "The Bridge"
  val theWell = "The Well"
  val theInn = "The Inn"
  val theLabyrinth = "The Labyrinth"
  val thePrison = "The Prison"
  val theDeath = "The Death"
  val theEnd = "The End"


  val tileSet: Set[TileDefinition] = ???
  val board: Board = Board("Original Goose Game", tileSet, Disposition.spiral(totalTiles))

  //2 dices
  val movementDice: MovementDice = Dice.Factory.randomMovement((1 to 6).toSet, "six sided dice")

  //To Win
  //To win you must reach square 63 exactly.
  // If your dice roll is more than you need then you move in to square 63 and then bounce back out again,
  // each spot on the dice is still one square in this move.
  // If you land on any of the special squares while you are doing this then you must follow the normal instructions.
  val bounceBackOnLastTile: BehaviourRule = ???

  //When you land on square 63 exactly you are the winner!
  val winningBehaviour: VictoryBehaviour = VictoryBehaviour()

  val stopOnTheEnd: BehaviourRule = (state: MatchState) => {
    val stopped = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.equals(state.getTile(theEnd).get))
    if (stopped) {
      Seq(Operation.trigger(s => Some(VictoryEvent(s.currentTurn, s.currentPlayer))))
    } else Seq()
  }

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

  //If your counter lands on a Goose square you can move again without throwing the dice.
  // You move the number of spots of your original throw.
  // For example throw a 4, land on a Goose, move four squares forward again.

  val stopOnGooseTile: BehaviourRule = (state: MatchState) => {
    val stoppedOnGoose = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.belongsTo(gooseTileGroup))

    if (stoppedOnGoose) {
      state.currentPlayer.history
        .filter(_.turn == state.currentTurn)
        .find(_.isInstanceOf[StepMovementEvent])
        .map(_.asInstanceOf[StepMovementEvent])
        .map(e => Operation.trigger(s => Some(StepMovementEvent(e.movement, s.currentPlayer, s.currentTurn))))
        .map(Seq(_)).getOrElse(Seq())
    }
    else Seq()
  }

  //If you land on the Bridge, square 6, miss a turn while you pay the toll.
  val stopOnBridge: BehaviourRule = (state: MatchState) => {
    val stopped = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.equals(state.getTile(theBridge).get))
    if (stopped) {
      Seq(Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn))))
    } else Seq()
  }

  //If you land on the Inn, square 19, miss a turn while you stop for some tasty dinner.
  val stopOnTheInn: BehaviourRule = (state: MatchState) => {
    val stopped = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.equals(state.getTile(theInn).get))
    if (stopped) {
      Seq(Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn))))
    } else Seq()
  }

  //If you you land on the Well, square 31, make a wish and miss three turns.
  //If another player passes you before your three turns are up you can start moving again on your next go.
  val stopOnTheWell: BehaviourRule = (state: MatchState) => {
    val stopped = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.equals(state.getTile(theWell).get))
    if (stopped) {
      Seq.fill(3)(Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn))))
    } else Seq()
  }

  val passedOnTheWell: BehaviourRule = (state: MatchState) => {
    val passedEvent: Option[PlayerPassedEvent] =
      state.getTile(theWell).get.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)
        .find(_.isInstanceOf[PlayerPassedEvent])
        .map(_.asInstanceOf[PlayerPassedEvent])

    if (passedEvent.isDefined) {
      passedEvent.get.consume()
      Seq(Operation.execute(s =>
        passedEvent.get.playerPassed.history =
          passedEvent.get.playerPassed.history.filter(e => !e.isConsumed && e.isInstanceOf[LoseTurnEvent])
      ))
    } else Seq()
  }

  //If you land on the Prison, square 52, you will have to miss three turns while you are behind bars.
  // If another player passes you before your three turns are up you can start moving again on your next go.

  val stopOnPrison: BehaviourRule = (state: MatchState) => {
    val stopped = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.equals(state.getTile(thePrison).get))
    if (stopped) {
      Seq.fill(3)(Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn))))
    } else Seq()
  }

  val passedOnPrison: BehaviourRule = (state: MatchState) => {
    val passedEvent: Option[PlayerPassedEvent] =
      state.getTile(thePrison).get.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)
        .find(_.isInstanceOf[PlayerPassedEvent])
        .map(_.asInstanceOf[PlayerPassedEvent])

    if (passedEvent.isDefined) {
      passedEvent.get.consume()
      Seq(Operation.execute(s =>
        passedEvent.get.playerPassed.history =
          passedEvent.get.playerPassed.history.filter(e => !e.isConsumed && e.isInstanceOf[LoseTurnEvent])
      ))
    } else Seq()
  }

  //If you land on the Labyrinth, square 42, you will get lost in the maze and have to move back to square 37.
  val stopOnLabyrinth: BehaviourRule = (state: MatchState) => {
    val stopped = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.equals(state.getTile(theLabyrinth).get))
    if (stopped) {
      Seq(Operation.execute(s => s.updatePlayerPiece(s.currentPlayer, p => Piece(p, s.getTile(37).map(Position(_))))))
    } else Seq()
  }

  //If you land on Death, square 58, you have to go back to square 1 and start all over again!
  val stopOnDeath: BehaviourRule = (state: MatchState) => {
    val stopped = state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .exists(_.tile.equals(state.getTile(theDeath).get))
    if (stopped) {
      Seq(Operation.execute(s => s.updatePlayerPiece(s.currentPlayer, p => Piece(p, s.getTile(1).map(Position(_))))))
    } else Seq()
  }

  //Players may not share squares, so if your dice roll would land you on an occupied square
  // you will have to stay where you are until it is your turn again.
  //TODO ?????


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
