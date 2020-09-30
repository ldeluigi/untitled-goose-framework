package untitled.goose.framework.dsl.prolog

import alice.tuprolog.lib.OOLibrary
import alice.tuprolog.{Prolog, Struct, Term, Theory}
import alice.{tuprolog => tp}
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.runtime._
import untitled.goose.framework.model.events.GameEvent

import scala.collection.JavaConverters

trait GooseDSLPrologExtension {
  implicit def modelCondition(s: String): GameState => Boolean =
    state => {
      val engine: Prolog = new Prolog()
      val lib: OOLibrary = engine.getLibrary("alice.tuprolog.lib.OOLibrary").asInstanceOf[OOLibrary]
      val gameStateId: Struct = Struct.atom("gamestate")
      lib.register(gameStateId, state)
      engine.addTheory(stateToTheory(state))
      lib.unregister(gameStateId)
      engine.solve(s).isSuccess
    }

  implicit class PimpedGameState(state: GameState) {
    def asPrologTheory: String = stateToTheory(state).getText
  }

  private def stateToTheory(state: GameState): Theory = {

    val currentTurn: Struct = createTerm("currentTurn", state.currentTurn)
    val currentCycle: Struct = createTerm("currentCycle", state.currentTurn)
    val currentPlayer: Struct = Struct.of("currentPlayer", toTerm(state.currentPlayer))
    val nextPlayer: Struct = Struct.of("nextPlayer", toTerm(state.nextPlayer))
    val ownsPiece: Iterable[Struct] = state.playerPieces.map(pl => owns(pl._1, pl._2))
    val gameBoard = Struct.of("gameBoard", toTerm(state.gameBoard))
    val consumableBuffer = Struct.of("consumableBuffer", toTerm(state.consumableBuffer))
    val gameHistory = Struct.of("gameHistory", toTerm(state.gameHistory))
    val players = Struct.of("players", Struct.list(JavaConverters.asJavaIterable(state.players.map(toTerm))))

    var clauses: Seq[Struct] = Seq(
      currentTurn,
      currentCycle,
      currentPlayer,
      nextPlayer,
      gameBoard,
      consumableBuffer,
      gameHistory,
      players
    )
    clauses ++= ownsPiece
    Theory.of(JavaConverters.asJavaCollection(clauses))
  }

  private def createTerm(name: String, value: Int): Struct = {
    Struct.of(name, tp.Int.of(value))
  }

  private def toTerm(player: Player): Struct = {
    Struct.of("player",
      Term.createTerm(player.name),
      Struct.of("history",
        toTerm(player.history))
    )
  }

  private def toTerm(piece: Piece): Struct = {
    Struct.of("piece", toTerm(piece.position), toTerm(piece.colour))
  }

  private def toTerm(position: Option[Position]): Struct = {
    Struct.of("position", position.map(p => toTerm(p.tile)).getOrElse(Term.createTerm("none")))
  }

  private def toTerm(tile: Tile): Struct =
    Struct.of("tile",
      tile.definition.number.map(tp.Int.of)
        .orElse(tile.definition.name.map(Term.createTerm))
        .getOrElse(Term.createTerm("emptyTile")),
      Struct.of("history", toTerm(tile.history))
    )

  private def toTerm(colour: Colour): Struct =
    Struct.of("colour", tp.Double.of(colour.red),
      tp.Double.of(colour.green),
      tp.Double.of(colour.blue)
    )

  private def owns(player: Player, piece: Piece): Struct = {
    Struct.of("owns", toTerm(player), toTerm(piece))
  }

  private def toTerm(board: Board): Struct = {
    Struct.of("board",
      Struct.list(JavaConverters.asJavaIterable(board.tiles.toList.map(toTerm))),
      Struct.of("first", toTerm(board.first))
    )
  }

  private def toTerm(history: Seq[GameEvent]): Struct = {
    Struct.list(JavaConverters.asJavaIterable(history.map(toTerm)))
  }

  private def toTerm(event: GameEvent): Struct = {
    // TODO add event groups
    Struct.of("event", Term.createTerm(event.name), createTerm("cycle", event.cycle), createTerm("turn", event.turn))
  }
}
