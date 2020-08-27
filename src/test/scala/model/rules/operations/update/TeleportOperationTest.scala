package model.rules.operations.update

import engine.events.consumable.{StopOnTileEvent, TileEnteredEvent, TileExitedEvent}
import model.entities.board.{Piece, Position, TileDefinition}
import model.game.GameState
import model.rules.operations.Operation
import model.{Player, Tile}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

abstract class TeleportOperationTest extends AnyFlatSpec with Matchers {

  //passo della roba all'input e vedo se è corretto quello che ritorna, che le op siano relative al player passato,
  // che il teleport è verso la tile che gli ho passayo
  //capire l'implementazione e verificarne la correttezza con input di un certo tipo.

  val gameState: GameState

  val tileDef: TileDefinition = TileDefinition(1)
  val tile: Tile = Tile(tileDef)

  val name: String = "gioco"
  val player: Player = Player(name)

  val operations: Seq[Operation]

  val teleport = TeleportOperation.apply(gameState, player, tile)

  behavior of "TeleportOperationTest"

  it should "return a correct sequence of Operations" in {
    val opSeq: Seq[Operation] = Seq()
    val tileExited = gameState.playerPieces(player).position.map(_.tile)
    if (tileExited.isDefined) {
      opSeq :+ Operation.trigger(TileExitedEvent(player, tileExited.get, gameState.currentTurn, gameState.currentCycle))
    }
    opSeq :+ Operation.updateState(gameState => {
      gameState.updatePlayerPiece(player, piece => {
        Piece(piece, Some(Position(tile)))
      })
    })
    opSeq :+ Operation.trigger(
      TileEnteredEvent(player, tile, gameState.currentTurn, gameState.currentCycle),
      StopOnTileEvent(player, tile, gameState.currentTurn, gameState.currentCycle)
    )
    teleport.equals(opSeq)
  }

}
