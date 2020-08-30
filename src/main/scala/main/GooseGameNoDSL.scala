package main

import java.awt.{Dimension, Toolkit}

import engine.events.consumable._
import engine.events.persistent.{GainTurnEvent, LoseTurnEvent, TileActivatedEvent, TurnEndedEvent}
import engine.events.{GameEvent, consumable}
import javafx.scene.input.KeyCode
import model.actions.{Action, RollMovementDice}
import model.entities.Dice.MovementDice
import model.entities.board._
import model.entities.{DialogContent, Dice}
import model.game.Game
import model.rules.ActionRule
import model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule
import model.rules.actionrules.LoseTurnActionRule
import model.rules.behaviours._
import model.rules.operations.Operation
import model.rules.operations.Operation.DialogOperation
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet, RulePriorities, RuleSet}
import model.{Color, Player, TileIdentifier}
import scalafx.application.JFXApp
import view.ApplicationController
import view.board.GraphicDescriptor


object GooseGameNoDSL extends JFXApp {

  import RulePriorities.DefaultPriorities
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
  val bounceBackOnLastTile: BehaviourRule = BehaviourRule[TileEnteredEvent](
    filterStrategy = _.tile.definition.name.contains(theEnd),
    operations = (events, state) => {
      events.map(e => Operation.trigger(InvertMovementEvent(e.player, state.currentTurn, state.currentCycle)))
    })

  //When you land on square 63 exactly you are the winner!
  val stopOnTheEnd: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.name.contains(theEnd),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        Operation.trigger(consumable.VictoryEvent(e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle))
      ))
    })

  //To Play:

  //If you throw a 3 on your first turn you can move straight to square 26.
  val teleportTo26OnFirstTurn: BehaviourRule = BehaviourRule[MovementDiceRollEvent](
    filterStrategy = _.diceResult.contains(3),
    when = _.currentPlayer.history.only[TurnEndedEvent].isEmpty,
    operations = (_, state) => {
      Seq(
        DialogOperation(new DialogContent {
          override def title: String = "Special first throw!"

          override def text: String = "You roll a 3 on your first turn, go to tile 26"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.trigger(TeleportEvent(state.getTile(26).get, state.currentPlayer, state.currentTurn, state.currentCycle))
      )
    },
    consume = false
  )

  //If your counter lands on a Goose square you can throw your dice again.

  val stopOnGooseTile: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.belongsTo(gooseTileGroup),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        Operation.trigger(GainTurnEvent(e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle)
        ),
        DialogOperation(new DialogContent {
          override def title: String = "Landed on a goose"

          override def text: String = "You can roll your dices again"

          override def options: Map[String, GameEvent] = Map()
        })
      ))
    }
  )

  //If you land on the Bridge, square 6, miss a turn while you pay the toll.
  val stopOnBridge: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.name.contains(theBridge),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        DialogOperation(new DialogContent {
          override def title: String = "The Bridge"

          override def text: String = "You landed on the Bridge. Miss a turn while you pay the toll"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.trigger(LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle))
      ))
    }
  )


  //If you land on the Inn, square 19, miss a turn while you stop for some tasty dinner.
  val stopOnTheInn: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.name.contains(theInn),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        DialogOperation(new DialogContent {
          override def title: String = "The Inn"

          override def text: String = "You landed on the Inn. Miss a turn while you stop for some tasty dinner"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.trigger(LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle))
      ))
    }
  )

  //If you you land on the Well, square 31, make a wish and miss three turns.
  //If another player passes you before your three turns are up you can start moving again on your next go.
  val stopOnTheWell: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.name.contains(theWell),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        Operation.trigger(
          LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle)
        ),
        DialogOperation(new DialogContent {
          override def title: String = "The Well"

          override def text: String = "You landed on the Well! Miss 3 turns and make a wish... " +
            "\nIf another player passes you before the three turns are up you can start moving again"

          override def options: Map[String, GameEvent] = Map()
        })
      ))
    }
  )

  val passedOnTheWell: BehaviourRule = BehaviourRule[PlayerPassedEvent](
    filterStrategy = _.tile.definition.name.contains(theWell),
    operations = (events, _) => {
      events.map(e => Operation.updateState(_ => {
        e.player.history.removeAll[LoseTurnEvent]()
      }))
    }
  )

  //If you land on the Prison, square 52, you will have to miss three turns while you are behind bars.
  // If another player passes you before your three turns are up you can start moving again on your next go.

  val stopOnPrison: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.name.contains(thePrison),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        Operation.trigger(
          LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          LoseTurnEvent(e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle)
        ),
        DialogOperation(new DialogContent {
          override def title: String = "The Prison"

          override def text: String = "You landed on the Prison! Miss 3 turns while you are behind bars" +
            "\nIf another player passes you before the three turns are up you can start moving again"

          override def options: Map[String, GameEvent] = Map()
        })
      ))
    }
  )

  val passedOnPrison: BehaviourRule = BehaviourRule[PlayerPassedEvent](
    filterStrategy = _.tile.definition.name.contains(thePrison),
    operations = (events, _) => {
      events.map(e => Operation.updateState(_ => {
        e.player.history.removeAll[LoseTurnEvent]()
      }))
    }
  )

  //If you land on the Labyrinth, square 42, you will get lost in the maze and have to move back to square 37.
  val stopOnLabyrinth: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.name.contains(theLabyrinth),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        DialogOperation(new DialogContent {
          override def title: String = "The Labyrinth"

          override def text: String = "You enter the labyrinth but you get lost. You exit on tile 37"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.trigger(
          TeleportEvent(state.getTile(37).get, e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle)
        )
      ))
    }
  )

  //If you land on Death, square 58, you have to go back to square 1 and start all over again!
  val stopOnDeath: BehaviourRule = BehaviourRule[StopOnTileEvent](
    filterStrategy = _.tile.definition.name.contains(theDeath),
    operations = (events, state) => {
      events.flatMap(e => Seq(
        DialogOperation(new DialogContent {
          override def title: String = "The Death"

          override def text: String = "You died. Go back to the beginning and try again to reach the end!"

          override def options: Map[String, GameEvent] = Map()
        }),
        Operation.trigger(
          TeleportEvent(state.getTile(1).get, e.player, state.currentTurn, state.currentCycle),
          TileActivatedEvent(e.tile, state.currentTurn, state.currentCycle)
        )
      ))
    }
  )
  //Players may not share squares, so if your dice roll would land you on an occupied square
  // you will have to stay where you are until it is your turn again.
  //TODO ?????


  //Framework behaviours
  val FrameworkBehaviours: Seq[BehaviourRule] = Seq(
    VictoryBehaviour(),
    MovementWithDiceBehaviour(),
    TeleportBehaviour(),
    MultipleStepBehaviour(),
    SkipTurnBehaviour()
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

  behaviourRule = behaviourRule ++ FrameworkBehaviours

  val actionRules: Set[ActionRule] = Set(rollDiceActionRule, LoseTurnActionRule(Set(rollAction)))

  val ruleSet: RuleSet = PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.randomOrder,
    1 to 10,
    actionRules,
    behaviourRule,
    Seq(),
  )

  //From a menu GUI that select and creates player and pieces on the press of a "Start game" button

  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  //List.range(1, 10).map(a => Player("P" + a) -> Piece()).toMap

  val currentMatch: Game = Game(board, players, ruleSet)

  //View launch
  val graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map()
  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    //fullScreen = true
    minWidth = 0.75 * screenSize.width
    minHeight = 0.75 * screenSize.height
    scene = ApplicationController(this, screenSize.width, screenSize.height, currentMatch, graphicMap)
    fullScreenExitHint = "ESC to exit full screen mode"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode.equals(KeyCode.F11)) {
      stage.setFullScreen(true)
    }
  )

}
