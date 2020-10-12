package untitled.goose.framework.model.entities.runtime

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.runtime.Player.PlayerImpl
import untitled.goose.framework.model.entities.runtime.PlayerDefinition.PlayerDefinitionImpl
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl

class MutableGameStateTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameMutableState: GameState = gameMatch.currentState

  val p1: Player = PlayerImpl(PlayerDefinitionImpl("P1"), Seq())
  val piece: Piece = Piece(Colour.Default.Blue)

  behavior of "MutableGameStateTest"

  it should "return the current turn of execution" in {
    gameMutableState.currentTurn should be(0)
  }

  it should "return a seq of runtime events" in {
    gameMutableState.gameHistory.size should be(0)
  }

  it should "return its current player of execution" in {
    gameMutableState.currentPlayer should equal(p1)
  }

  it should "update a player pieces as told and check correctness" in {
    gameMutableState.updatePlayerPiece(p1.definition, _ => piece).playerPieces(p1.definition).colour should equal(piece.colour)
  }

  it should "return a seq of consumable runtime events" in {
    gameMutableState.consumableBuffer.size should be(0)
  }

}
