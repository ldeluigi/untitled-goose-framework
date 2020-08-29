package model.rules.operations.update

import mock.MatchMock
import model.Player
import model.game.{Game, GameState}
import org.scalatest.flatspec.AnyFlatSpec

class StepOperationTest extends AnyFlatSpec {

  var gameMatch: Game = MatchMock.default
  val gameState: GameState = gameMatch.currentState
  val steps: Int = 3
  val player: Player = Player("P1")
  val forward: Boolean = true

  behavior of "StepOperationTest"

  it should "return a correct sequence of Operations" in {
    //StepOperation.apply(gameState,steps,player, forward) should contain theSameElementsAs opSeq
    pending
  }

}
