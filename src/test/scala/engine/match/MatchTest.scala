package engine.`match`

import engine.events.{DiceRollEvent, TileEnteredEvent, TurnShouldEndEvent}
import engine.events.root.GameEvent
import model.{Color, Player}
import model.entities.board.{Board, Disposition, Piece}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet}
import org.scalatest.OneInstancePerTest
import org.scalatest.flatspec.AnyFlatSpec

class MatchTest extends AnyFlatSpec with OneInstancePerTest {

  val board: Board = Board(10, Disposition.snake(10))
  val p1: Player = Player("P1")
  val p2: Player = Player("P2")
  val players: Map[Player, Piece] = Map(p1 -> Piece(Color.Red), p2 -> Piece(Color.Blue))
  val gameMatch: Match = Match(board, players, PriorityRuleSet(playerOrdering = PlayerOrdering.givenOrder(Seq(p1, p2))))

  "A Match" should "have a MatchBoard" in {
    assert(gameMatch.board.equals(MatchBoard(board)))
  }

  it should "have a set of players" in {
    assert(gameMatch.players.equals(players.keySet))
  }

  it should "have a current MatchState" in {
    assert(gameMatch.currentState != null)
  }

  it should "submit general events to the stateHistory" in {
    val gameEvent = TurnShouldEndEvent(gameMatch.currentState.currentTurn)
    gameMatch.submitEvent(gameEvent)
    assert(gameMatch.currentState.history.contains(gameEvent))
  }

  it should "submit events to players" in {
    val state = gameMatch.currentState
    val playerEvent = DiceRollEvent(state.currentPlayer, 1, state.currentTurn)
    gameMatch.submitEvent(playerEvent)

    assert(state.currentPlayer.history.contains(playerEvent))
  }

  it should "submit events to tiles" in {
    val state = gameMatch.currentState
    val tileEvent = TileEnteredEvent(state.currentPlayer, state.matchBoard.first, state.currentTurn)
    gameMatch.submitEvent(tileEvent)

    assert(state.matchBoard.first.history.contains(tileEvent))
  }

  "A matchState" should "have a current player" in {
    val state = gameMatch.currentState
    assert(state.currentPlayer.equals(p1))
  }

  it should "know who is the next player" in {
    val state = gameMatch.currentState
    assert(state.nextPlayer.equals(p2))
  }

  //TODO complete this or remove
  it should "know where the playerPieces are" in {
    pending
  }

  it should "update player pieces as told" in {
    pending
  }

}
