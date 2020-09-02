package model.rules.operations.update

import mock.MatchMock
import model.entities.board.TileDefinition
import model.events.consumable.{StopOnTileEvent, TileEnteredEvent, TileExitedEvent}
import model.game.{Game, GameState, Piece, Player, Position, Tile}
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TeleportOperationTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameState: GameState = gameMatch.currentState
  val tileDef: TileDefinition = TileDefinition(1)
  val tile: Tile = Tile(tileDef)
  val player: Player = Player("P1")

  var opSeq: Seq[Operation] = Seq()
  val tileExited: Option[Tile] = gameState.playerPieces(player).position.map(_.tile)
  if (tileExited.isDefined) {
    opSeq :+ Operation.trigger(TileExitedEvent(player, tileExited.get, gameState.currentTurn, gameState.currentCycle))
  }
  opSeq = opSeq :+ Operation.updateState(gameState => {
    gameState.updatePlayerPiece(player, piece => {
      Piece(piece, Some(Position(tile)))
    })
  })
  opSeq = opSeq :+ Operation.trigger(
    TileEnteredEvent(player, tile, gameState.currentTurn, gameState.currentCycle),
    StopOnTileEvent(player, tile, gameState.currentTurn, gameState.currentCycle)
  )

  behavior of "TeleportOperationTest"

  it should "return a correct sequence of Operations" in {
    //TeleportOperation.apply(gameState, player, tile).size should equal(opSeq.size) -> CORRECT
    //TeleportOperation.apply(gameState, player, tile) should be(opSeq) -> INCORRECT
    pending
  }

}
