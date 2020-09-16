package untitled.goose.framework.model.rules.behaviours

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.GameStateExtensions.MutableStateExtensions
import untitled.goose.framework.model.entities.runtime.{Game, MutableGameState}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, DialogLaunchEvent}
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

class DialogLaunchBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "DialogLaunchBehaviourTest"

  val game: Game = MatchMock.default
  val state: MutableGameState = game.currentState

  val dialogContent: DialogContent = DialogContent("Title", "Content")
  val dialogOperation: Operation = DialogOperation(dialogContent)
  val dialogLaunchEvent: ConsumableGameEvent = DialogLaunchEvent(game.currentState.currentTurn, game.currentState.currentCycle, dialogContent)
  var operationSequence: Seq[Operation] = Seq()

  override protected def beforeEach(): Unit = {
    state.submitEvent(dialogLaunchEvent)
    operationSequence = DialogLaunchBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
  }

  it should "check that the supposed DialogOperation has been returned" in {
    operationSequence should contain(dialogOperation)
  }

  it should "check that itself has been consumed" in {
    state.consumableBuffer should not contain dialogLaunchEvent
  }

}