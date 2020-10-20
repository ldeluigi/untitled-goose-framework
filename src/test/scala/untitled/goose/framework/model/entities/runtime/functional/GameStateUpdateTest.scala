package untitled.goose.framework.model.entities.runtime.functional

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.runtime._
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, SkipTurnEvent, TurnShouldEndEvent}
import untitled.goose.framework.model.events.persistent.{TileActivatedEvent, TurnEndedEvent}

class GameStateUpdateTest extends AnyFlatSpec with Matchers {

  behavior of "GameStateUpdateTest"

  val gameMatch: Game = MatchMock.default
  val gameState: GameState = gameMatch.currentState
  val p1: Player = gameState.players.values.head
  val p2: Player = Player(MatchMock.p2)
  val tile: Tile = gameState.gameBoard.tiles.values.head
  val piece: Piece = Piece(Colour.Default.Blue)

  it should "updateConsumableBuffer" in {
    val buf = Seq(TurnShouldEndEvent(1, 1))
    gameState.updateConsumableBuffer(_ => buf).consumableBuffer should equal(buf)
  }

  it should "updateGameHistory" in {
    val his = Seq(TurnEndedEvent(p1.definition, 1, 1))
    gameState.updateGameHistory(_ => his).gameHistory should equal(his)
  }

  it should "updateCurrentPlayer" in {
    gameState.updateCurrentPlayer((_, _) => p2).currentPlayer should equal(p2.definition)
  }

  it should "updatePlayerPiece" in {
    gameState
      .updatePlayerPiece(p1.definition, _ => piece)
      .playerPieces(p1.definition).colour should equal(piece.colour)
  }

  it should "updatePlayerHistory" in {
    val his = Seq(TurnEndedEvent(p1.definition, 1, 1))
    gameState
      .updatePlayerHistory(p1.definition, _ => his)
      .players(p1.definition)
      .history should equal(his)
  }

  it should "updateTileHistory" in {
    val his = Seq(TileActivatedEvent(tile.definition, 1, 1))
    gameState
      .updateTileHistory(tile.definition, _ => his)
      .gameBoard
      .tiles(tile.definition)
      .history should equal(his)
  }

  it should "updateCurrentCycle" in {
    gameState.updateCurrentCycle(_ => 7).currentCycle should be(7)
  }

  it should "updateCurrentTurn" in {
    gameState.updateCurrentTurn(_ => 8).currentTurn should be(8)
  }

  it should "submit a given event in the right histories" in {
    val gameMatch: Game = MatchMock.default
    val gameMutableState: GameState = gameMatch.currentState
    val skipTurnEvent: ConsumableGameEvent = SkipTurnEvent(gameMatch.currentState.currentPlayer, gameMatch.currentState.currentTurn, gameMatch.currentState.currentCycle)

    gameMutableState.submitEvent(skipTurnEvent).consumableBuffer should contain(skipTurnEvent)
  }

  it should "save a consumable event onto the correct persistent history" in {
    val gameMatch: Game = MatchMock.default
    val gameMutableState: GameState = gameMatch.currentState
    val skipTurnEvent: ConsumableGameEvent = SkipTurnEvent(gameMatch.currentState.currentPlayer, gameMatch.currentState.currentTurn, gameMatch.currentState.currentCycle)
    gameMutableState.saveEvent(skipTurnEvent).gameHistory should contain(skipTurnEvent)
  }

}
