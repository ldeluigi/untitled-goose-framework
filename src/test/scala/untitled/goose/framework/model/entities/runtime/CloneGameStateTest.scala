package untitled.goose.framework.model.entities.runtime

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.GameStateExtensions.MutableStateExtensions
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, StopOnTileEvent}
import untitled.goose.framework.model.events.persistent.{GainTurnEvent, TileActivatedEvent}
import untitled.goose.framework.model.events.{GameEvent, PlayerEvent, TileEvent}

class CloneGameStateTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  var state: MutableGameState = MatchMock.default.currentState
  var clonedGameState: GameState = state.clone()

  override protected def beforeEach(): Unit = {
    state = MatchMock.default.currentState
    clonedGameState = state.clone()
  }

  behavior of "CloneGameState"

  it should "Not change turn when the original state is updated" in {
    state.currentTurn = 10
    clonedGameState.currentTurn should not be state.currentTurn
  }

  it should "Not change current player when the original state is updated" in {
    state.currentPlayer = state.nextPlayer
    clonedGameState.currentPlayer should not be state.currentPlayer
  }


  it should "Not change consumable history when the original state is updated" in {
    val event: GameEvent = StopOnTileEvent(state.currentPlayer, state.gameBoard.first, state.currentTurn, state.currentCycle)
    state.submitEvent(event)
    clonedGameState.consumableBuffer should not contain event
  }

  it should "Not change runtime history when the original state is updated" in {
    val event: ConsumableGameEvent = StopOnTileEvent(state.currentPlayer, state.gameBoard.first, state.currentTurn, state.currentCycle)
    state.saveEvent(event)
    clonedGameState.gameHistory should not contain event
  }

  it should "Not change player pieces position when the original state is updated" in {
    state.updatePlayerPiece(state.currentPlayer,
      piece => Piece(piece, state.gameBoard.next(state.gameBoard.first).map(Position(_))))

    clonedGameState.playerPieces(state.currentPlayer) should not be state.playerPieces(state.currentPlayer)
  }

  it should "Not change player history when the original state is updated" in {
    val event: PlayerEvent = GainTurnEvent(state.currentPlayer, state.currentTurn, state.currentCycle)
    state.submitEvent(event)
    clonedGameState.currentPlayer.history should not contain event
  }

  it should "Not change tile history when the original state is updated" in {
    val event: TileEvent = TileActivatedEvent(state.gameBoard.first, state.currentTurn, state.currentCycle)
    state.submitEvent(event)
    clonedGameState.gameBoard.first.history should not contain event
  }


}
