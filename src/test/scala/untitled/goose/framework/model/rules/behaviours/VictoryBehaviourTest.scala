package untitled.goose.framework.model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, DialogLaunchEvent, VictoryEvent}
import untitled.goose.framework.model.events.special.ExitEvent
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

class VictoryBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "VictoryBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val winningDialogContent: DialogContent = DialogContent("Victory!", "Winning players: " + game.currentState.currentPlayer.name, "Quit" -> ExitEvent)
  val dialogOperation: Operation = DialogOperation(winningDialogContent)
  val dialogLaunchEvent: ConsumableGameEvent = DialogLaunchEvent(game.currentState.currentTurn, game.currentState.currentCycle, winningDialogContent)
  val victoryEvent: ConsumableGameEvent = VictoryEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)

  it should "check that the supposed winning DialogOperation has been returned" in {
    val (_, ops) = VictoryBehaviour().applyRule(state.submitEvent(victoryEvent))
    val content = ops.find(_.isInstanceOf[DialogOperation]).map(_.asInstanceOf[DialogOperation].content)
    content.isDefined should be(true)
    content.get should equal(winningDialogContent)
  }

  it should "check that itself has been consumed" in {
    val (s, ops) = VictoryBehaviour().applyRule(state.submitEvent(victoryEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should not contain dialogLaunchEvent
  }

}
