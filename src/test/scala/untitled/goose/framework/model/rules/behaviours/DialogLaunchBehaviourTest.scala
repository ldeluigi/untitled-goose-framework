package untitled.goose.framework.model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, DialogLaunchEvent}
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

class DialogLaunchBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "DialogLaunchBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val dialogContent: DialogContent = DialogContent("Title", "Content")
  val dialogOperation: Operation = DialogOperation(dialogContent)
  val dialogLaunchEvent: ConsumableGameEvent = DialogLaunchEvent(game.currentState.currentTurn, game.currentState.currentCycle, dialogContent)

  it should "check that the supposed DialogOperation has been returned" in {
    DialogLaunchBehaviour().applyRule(state.submitEvent(dialogLaunchEvent))._2 should contain(dialogOperation)
  }

  it should "check that itself has been consumed" in {
    DialogLaunchBehaviour().applyRule(state.submitEvent(dialogLaunchEvent))._1.consumableBuffer should not contain dialogLaunchEvent
  }

}