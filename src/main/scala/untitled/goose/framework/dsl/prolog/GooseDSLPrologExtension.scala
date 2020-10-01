package untitled.goose.framework.dsl.prolog

import alice.tuprolog.lib.OOLibrary
import alice.tuprolog.{Prolog, Struct, Term, Theory}
import alice.{tuprolog => tp}
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.runtime._
import untitled.goose.framework.model.events.GameEvent

import scala.collection.JavaConverters

trait GooseDSLPrologExtension {

  /**
   * This method parses the given string as a prolog goal and returns true if goal is solved successfully.
   * The goal is solved after applying a theory generated from the GameState (input).
   *
   * An example of such theory, taken form the GooseGame, is:
   * <pre>
   * currentTurn(4).
   * currentCycle(4).
   * currentPlayer(player(asd,history([event(TurnEndedEvent,cycle(2),turn(2)),event(TurnEndedEvent,cycle(0),turn(0)),event(MovementDiceRollEvent,cycle(0),turn(0))]))).
   * nextPlayer(player(dsa,history([event(TurnEndedEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(3),turn(3)),event(TurnEndedEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(1),turn(1))]))).
   * gameBoard(board([tile(4,history([])),tile(9,history([])),tile(55,history([])),tile(41,history([])),tile(23,history([])),tile(36,history([])),tile(24,history([])),tile(51,history([])),tile(13,history([])),tile(19,history([])),tile(45,history([])),tile(56,history([])),tile(28,history([])),tile(60,history([])),tile(5,history([])),tile(59,history([])),tile(27,history([])),tile(37,history([])),tile(20,history([])),tile(32,history([])),tile(8,history([])),tile(52,history([])),tile(12,history([])),tile(40,history([])),tile(1,history([])),tile(31,history([])),tile(33,history([])),tile(44,history([])),tile(63,history([])),tile(16,history([])),tile(21,history([])),tile(43,history([])),tile(58,history([])),tile(26,history([])),tile(11,history([])),tile(53,history([])),tile(48,history([])),tile(6,history([event(TileActivatedEvent,cycle(0),turn(0))])),tile(38,history([])),tile(2,history([])),tile(34,history([])),tile(47,history([])),tile(62,history([])),tile(17,history([])),tile(49,history([])),tile(15,history([])),tile(30,history([])),tile(10,history([])),tile(42,history([])),tile(39,history([])),tile(25,history([])),tile(54,history([])),tile(22,history([])),tile(7,history([])),tile(57,history([])),tile(35,history([])),tile(29,history([])),tile(50,history([])),tile(18,history([])),tile(3,history([])),tile(46,history([])),tile(14,history([])),tile(61,history([]))],first(tile(1,history([]))))).
   * consumableBuffer([event(MovementDiceRollEvent,cycle(4),turn(4))]).
   * gameHistory([event(MovementDiceRollEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(0),turn(0))]).
   * players([player(asd,history([event(TurnEndedEvent,cycle(2),turn(2)),event(TurnEndedEvent,cycle(0),turn(0)),event(MovementDiceRollEvent,cycle(0),turn(0))])),player(dsa,history([event(TurnEndedEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(3),turn(3)),event(TurnEndedEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(1),turn(1))]))]).
   * owns(player(asd,history([event(TurnEndedEvent,cycle(2),turn(2)),event(TurnEndedEvent,cycle(0),turn(0)),event(MovementDiceRollEvent,cycle(0),turn(0))])),piece(position(tile(6,history([event(TileActivatedEvent,cycle(0),turn(0))]))),colour(1.0,0.0,0.0))).
   * owns(player(dsa,history([event(TurnEndedEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(3),turn(3)),event(TurnEndedEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(1),turn(1))])),piece(position(tile(34,history([]))),colour(0.0,1.0,0.0))).
   * </pre>
   *
   * @param s the prolog goal. It must have valid syntax.
   * @return true if theory and goal are compiled, and goal is solved successfully.
   */
  def state2p(s: String): GameState => Boolean =
    state => {
      val engine: Prolog = new Prolog()
      val lib: OOLibrary = engine.getLibrary("alice.tuprolog.lib.OOLibrary").asInstanceOf[OOLibrary]
      val gameStateId: Struct = Struct.atom("gamestate")
      lib.register(gameStateId, state)
      engine.addTheory(stateToTheory(state))
      val res: Boolean = engine.solve(s).isSuccess
      engine.solveEnd()
      lib.unregister(gameStateId)
      res
    }

  /**
   * This method parses the given string as a prolog goal and returns true if goal is solved successfully.
   * The goal is solved after applying a theory generated from the GameEvent (input).
   *
   * An example of such theory is:
   * <pre>
   * event(TurnEndedEvent,cycle(2),turn(2))
   * </pre>
   *
   * @param s the prolog goal. It must have valid syntax.
   * @return true if theory and goal are compiled, and goal is solved successfully.
   */
  def event2p(s: String): GameEvent => Boolean =
    event => {
      val engine: Prolog = new Prolog()
      val lib: OOLibrary = engine.getLibrary("alice.tuprolog.lib.OOLibrary").asInstanceOf[OOLibrary]
      val gameEventId: Struct = Struct.atom("gameevent")
      lib.register(gameEventId, event)
      engine.addTheory(Theory.of(toTerm(event)))
      val res: Boolean = engine.solve(s).isSuccess
      engine.solveEnd()
      lib.unregister(gameEventId)
      res
    }

  /** Enables printing the state as a prolog theory. */
  implicit class PimpedGameState(state: GameState) {

    /** Returns the prolog code generated from the state.
     * This theory allows for expressions on the game state
     * written in prolog.
     *
     * Output example:
     * <pre>
     * currentTurn(4).
     * currentCycle(4).
     * currentPlayer(player(asd,history([event(TurnEndedEvent,cycle(2),turn(2)),event(TurnEndedEvent,cycle(0),turn(0)),event(MovementDiceRollEvent,cycle(0),turn(0))]))).
     * nextPlayer(player(dsa,history([event(TurnEndedEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(3),turn(3)),event(TurnEndedEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(1),turn(1))]))).
     * gameBoard(board([tile(4,history([])),tile(9,history([])),tile(55,history([])),tile(41,history([])),tile(23,history([])),tile(36,history([])),tile(24,history([])),tile(51,history([])),tile(13,history([])),tile(19,history([])),tile(45,history([])),tile(56,history([])),tile(28,history([])),tile(60,history([])),tile(5,history([])),tile(59,history([])),tile(27,history([])),tile(37,history([])),tile(20,history([])),tile(32,history([])),tile(8,history([])),tile(52,history([])),tile(12,history([])),tile(40,history([])),tile(1,history([])),tile(31,history([])),tile(33,history([])),tile(44,history([])),tile(63,history([])),tile(16,history([])),tile(21,history([])),tile(43,history([])),tile(58,history([])),tile(26,history([])),tile(11,history([])),tile(53,history([])),tile(48,history([])),tile(6,history([event(TileActivatedEvent,cycle(0),turn(0))])),tile(38,history([])),tile(2,history([])),tile(34,history([])),tile(47,history([])),tile(62,history([])),tile(17,history([])),tile(49,history([])),tile(15,history([])),tile(30,history([])),tile(10,history([])),tile(42,history([])),tile(39,history([])),tile(25,history([])),tile(54,history([])),tile(22,history([])),tile(7,history([])),tile(57,history([])),tile(35,history([])),tile(29,history([])),tile(50,history([])),tile(18,history([])),tile(3,history([])),tile(46,history([])),tile(14,history([])),tile(61,history([]))],first(tile(1,history([]))))).
     * consumableBuffer([event(MovementDiceRollEvent,cycle(4),turn(4))]).
     * gameHistory([event(MovementDiceRollEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(0),turn(0))]).
     * players([player(asd,history([event(TurnEndedEvent,cycle(2),turn(2)),event(TurnEndedEvent,cycle(0),turn(0)),event(MovementDiceRollEvent,cycle(0),turn(0))])),player(dsa,history([event(TurnEndedEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(3),turn(3)),event(TurnEndedEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(1),turn(1))]))]).
     * owns(player(asd,history([event(TurnEndedEvent,cycle(2),turn(2)),event(TurnEndedEvent,cycle(0),turn(0)),event(MovementDiceRollEvent,cycle(0),turn(0))])),piece(position(tile(6,history([event(TileActivatedEvent,cycle(0),turn(0))]))),colour(1.0,0.0,0.0))).
     * owns(player(dsa,history([event(TurnEndedEvent,cycle(3),turn(3)),event(MovementDiceRollEvent,cycle(3),turn(3)),event(TurnEndedEvent,cycle(1),turn(1)),event(MovementDiceRollEvent,cycle(1),turn(1))])),piece(position(tile(34,history([]))),colour(0.0,1.0,0.0))).
     * </pre>
     */
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
