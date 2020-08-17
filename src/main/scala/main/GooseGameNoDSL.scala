package main

import java.awt.{Dimension, Toolkit}

import engine.events._
import engine.events.root.GameEvent
import javafx.scene.input.KeyCode
import model.actions.{Action, RollMovementDice}
import model.entities.Dice.MovementDice
import model.entities.board._
import model.entities.{DialogContent, Dice}
import model.game.{Game, GameState}
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.actionrules.LoseTurnActionRule
import model.rules.behaviours._
import model.rules.operations.{Operation, SpecialOperation}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RuleSet}
import model.rules.{ActionRule, BehaviourRule}
import model.{Color, Player, Tile}
import scalafx.application.JFXApp
import view.ApplicationController


object GooseGameNoDSL extends JFXApp {

  import model.game.GameStateExtensions._

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

  val board: Board = Board.create()
    .withNumberedTiles(totalTiles)
    .withName("Original Goose Game")
    .withDisposition(Disposition.spiral(_))
    .withNamedTile(6, theBridge)
    .withNamedTile(19, theInn)
    .withNamedTile(31, theWell)
    .withNamedTile(42, theLabyrinth)
    .withNamedTile(52, thePrison)
    .withNamedTile(58, theDeath)
    .withNamedTile(63, theEnd)
    .withGroupedTiles(gooseTileGroup, 5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59)
    .complete()

  //2 dices
  val movementDice: MovementDice = Dice.Factory.randomMovement((1 to 6).toSet, "six sided dice")


  //Each turn one player can:
  //Roll the dice and move the piece one square for the number resulted from the sum of the dice result.
  val rollAction: Action = RollMovementDice(movementDice, 2)
  val rollDiceActionRule: ActionRule = AlwaysPermittedActionRule(1, rollAction)

  //To Win
  //To win you must reach square 63 exactly.
  // If your dice roll is more than you need then you move in to square 63 and then bounce back out again,
  // each spot on the dice is still one square in this move.
  // If you land on any of the special squares while you are doing this then you must follow the normal instructions.
  val bounceBackOnLastTile: BehaviourRule = (state: GameState) =>
    state.getTile(theEnd).get.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[TileEnteredEvent])
      .map(_.asInstanceOf[TileEnteredEvent])
      .map(e => {
        e.consume()
        e
      })
      .map(_ => Seq(Operation.trigger(s => Some(InvertMovementEvent(s.currentPlayer, s.currentTurn)))))
      .getOrElse(Seq())

  //When you land on square 63 exactly you are the winner!

  val stopOnTheEnd: BehaviourRule = (state: GameState) => {
    val stopped = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.equals(state.getTile(theEnd).get))
      .map(e => {
        e.consume()
        e
      })
    if (stopped.isDefined) {
      Seq(
        Operation.trigger(s => Some(VictoryEvent(s.currentTurn, s.currentPlayer))),
        Operation.trigger(s => Some(TileActivatedEvent(stopped.get.tile, s.currentTurn)))
      )
    } else Seq()
  }

  //To Play:

  //If you throw a 3 on your first turn you can move straight to square 26.
  val teleportTo26OnFirstTurn: BehaviourRule = (state: GameState) => {
    if (state.getTile(26).isDefined &&
      !state.currentPlayer.history.exists(_.isInstanceOf[TurnEndedEvent])) { //is first turn
      val teleportTo: Tile = state.getTile(26).get
      state.currentPlayer.history
        .filterTurn(state.currentTurn)
        .filterNotConsumed()
        .find(_.isInstanceOf[MovementDiceRollEvent])
        .map(_.asInstanceOf[MovementDiceRollEvent])
        .filter(_.res.contains(3))
        .map(e => {
          e.consume()
          e
        })
        .map(_ => Seq(
          SpecialOperation.DialogOperation(s => new DialogContent {
            override def title: String = "Special first throw!"

            override def text: String = "You roll a 3 on your first turn, go to tile 26"

            override def options: Map[String, GameEvent] = Map()
          }),
          Operation.trigger(s => Some(TeleportEvent(teleportTo, state.currentPlayer, state.currentTurn))))
        )
        .getOrElse(Seq())
    }
    else Seq()
  }

  //If your counter lands on a Goose square you can move again without throwing the dice.
  // You move the number of spots of your original throw.
  // For example throw a 4, land on a Goose, move four squares forward again.

  val stopOnGooseTile: BehaviourRule = (state: GameState) => {
    val stoppedOnGoose = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.belongsTo(gooseTileGroup))
      .map(e => {
        e.consume()
        e
      })

    if (stoppedOnGoose.isDefined) {
      state.currentPlayer.history
        .filterTurn(state.currentTurn)
        .find(_.isInstanceOf[StepMovementEvent])
        .map(_.asInstanceOf[StepMovementEvent])
        .map(_ => Operation.trigger(s => Some(GainTurnEvent(state.currentPlayer, s.currentTurn))))
        .map(Seq(
          _,
          SpecialOperation.DialogOperation(s => new DialogContent {
            override def title: String = "Landed on a goose"

            override def text: String = "You can roll your dices again"

            override def options: Map[String, GameEvent] = Map()
          }),
          Operation.trigger(s => Some(TileActivatedEvent(stoppedOnGoose.get.tile, s.currentTurn)))))
        .getOrElse(Seq())
    }
    else Seq()
  }

  //If you land on the Bridge, square 6, miss a turn while you pay the toll.
  val stopOnBridge: BehaviourRule = (state: GameState) => {
    val stopped = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.equals(state.getTile(theBridge).get))
      .map(e => {
        e.consume()
        e
      })
    if (stopped.isDefined) {
      Seq(
        SpecialOperation.DialogOperation(s => new DialogContent {
          override def title: String = "The Bridge"

          override def text: String = "You landed on the Bridge. Miss a turn while you pay the toll"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn))),
        Operation.trigger(s => Some(TileActivatedEvent(stopped.get.tile, s.currentTurn)))
      )
    } else Seq()
  }

  //If you land on the Inn, square 19, miss a turn while you stop for some tasty dinner.
  val stopOnTheInn: BehaviourRule = (state: GameState) => {
    val stopped = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.equals(state.getTile(theInn).get))
      .map(e => {
        e.consume()
        e
      })
    if (stopped.isDefined) {
      Seq(
        SpecialOperation.DialogOperation(s => new DialogContent {
          override def title: String = "The Inn"

          override def text: String = "You landed on the Inn. Miss a turn while you stop for some tasty dinner"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn))),
        Operation.trigger(s => Some(TileActivatedEvent(stopped.get.tile, s.currentTurn)))
      )
    } else Seq()
  }

  //If you you land on the Well, square 31, make a wish and miss three turns.
  //If another player passes you before your three turns are up you can start moving again on your next go.
  val stopOnTheWell: BehaviourRule = (state: GameState) => {
    val stopped = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.equals(state.getTile(theWell).get))
      .map(e => {
        e.consume()
        e
      })

    if (stopped.isDefined) {
      Seq.fill(3)(Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn)))) :+
        Operation.trigger(s => Some(TileActivatedEvent(stopped.get.tile, s.currentTurn))) :+
        SpecialOperation.DialogOperation(s => new DialogContent {
          override def title: String = "The Well"

          override def text: String = "You landed on the Well! Miss 3 turns and make a wish... if another player passes you before the three turns are up you can start moving again"

          override def options: Map[String, GameEvent] = Map()
        })
    } else Seq()
  }

  val passedOnTheWell: BehaviourRule = (state: GameState) => {
    val passedEvent: Option[PlayerPassedEvent] =
      state.getTile(theWell).get.history
        .filterTurn(state.currentTurn)
        .filterNotConsumed()
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

  val stopOnPrison: BehaviourRule = (state: GameState) => {
    val stopped = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.equals(state.getTile(thePrison).get))
      .map(e => {
        e.consume()
        e
      })
    if (stopped.isDefined) {
      Seq.fill(3)(Operation.trigger(s => Some(LoseTurnEvent(state.currentPlayer, s.currentTurn)))) :+
        Operation.trigger(s => Some(TileActivatedEvent(stopped.get.tile, s.currentTurn))) :+
        SpecialOperation.DialogOperation(s => new DialogContent {
          override def title: String = "The Prison"

          override def text: String = "You landed on the Prison! Miss 3 turns while you are behind bars"

          override def options: Map[String, GameEvent] = Map()
        })
    } else Seq()
  }

  val passedOnPrison: BehaviourRule = (state: GameState) => {
    val passedEvent: Option[PlayerPassedEvent] =
      state.getTile(thePrison).get.history
        .filterTurn(state.currentTurn)
        .filterNotConsumed()
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
  val stopOnLabyrinth: BehaviourRule = (state: GameState) => {
    val stopped = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.equals(state.getTile(theLabyrinth).get))
      .map(e => {
        e.consume()
        e
      })

    if (stopped.isDefined) {
      Seq(
        SpecialOperation.DialogOperation(s => new DialogContent {
          override def title: String = "The Labyrinth"

          override def text: String = "You enter the labyrinth but you get lost. You exit on tile 37"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.execute(s => s.updatePlayerPiece(s.currentPlayer, p => Piece(p, s.getTile(37).map(Position(_))))),
        Operation.trigger(s => Some(TileActivatedEvent(stopped.get.tile, s.currentTurn)))
      )
    } else Seq()
  }

  //If you land on Death, square 58, you have to go back to square 1 and start all over again!
  val stopOnDeath: BehaviourRule = (state: GameState) => {
    val stopped = state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .find(_.isInstanceOf[StopOnTileEvent])
      .map(_.asInstanceOf[StopOnTileEvent])
      .find(_.tile.equals(state.getTile(theDeath).get))
      .map(e => {
        e.consume()
        e
      })
    if (stopped.isDefined) {
      Seq(
        SpecialOperation.DialogOperation(s => new DialogContent {
          override def title: String = "The Death"

          override def text: String = "You died. Go back to the beginning and try again to reach the end!"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.execute(s => s.updatePlayerPiece(s.currentPlayer, p => Piece(p, s.getTile(1).map(Position(_))))),
        Operation.trigger(s => Some(TileActivatedEvent(stopped.get.tile, s.currentTurn)))
      )
    } else Seq()
  }

  //Players may not share squares, so if your dice roll would land you on an occupied square
  // you will have to stay where you are until it is your turn again.
  //TODO ?????


  //Framework behaviours
  val FrameworkBehaviours: Seq[BehaviourRule] = Seq(
    VictoryBehaviour(),
    MovementWithDiceBehaviour(),
    TeleportBehaviour(),
    MultipleStepBehaviour(),
    SkipTurnBehaviour(),
    DialogLaunchBehaviour()
  )


  var behaviourRule: Seq[BehaviourRule] =
    Seq(
      bounceBackOnLastTile,
      stopOnTheEnd,
      teleportTo26OnFirstTurn,
      stopOnGooseTile,
      stopOnBridge,
      stopOnTheInn,
      passedOnTheWell,
      stopOnTheWell,
      passedOnPrison,
      stopOnPrison,
      stopOnDeath,
      stopOnLabyrinth
    )

  behaviourRule = TurnEndEventBehaviour() +: (behaviourRule ++ FrameworkBehaviours)

  val actionRules: Set[ActionRule] = Set(rollDiceActionRule, LoseTurnActionRule(Set(rollAction)))

  val ruleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.orderedRandom,
    actionRules,
    behaviourRule
  )

  //From a menu GUI that select and creates player and pieces on the press of a "Start game" button

  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  //List.range(1, 10).map(a => Player("P" + a) -> Piece()).toMap

  val currentMatch: Game = Game(board, players, ruleSet)

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
