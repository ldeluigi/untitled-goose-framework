package untitled.goose.framework.model.rules.behaviours

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, DialogLaunchEvent, VictoryEvent}
import untitled.goose.framework.model.events.special.ExitEvent
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

class VictoryBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "VictoryBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val winningDialogContent: DialogContent = DialogContent("Victory!", "Winning players: " + game.currentState.currentPlayer.name, "Quit" -> ExitEvent)
  val dialogOperation: Operation = DialogOperation(winningDialogContent)
  val dialogLaunchEvent: ConsumableGameEvent = DialogLaunchEvent(game.currentState.currentTurn, game.currentState.currentCycle, winningDialogContent)
  val victoryEvent: ConsumableGameEvent = VictoryEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  var operationSequence: Seq[Operation] = Seq()

  override protected def beforeEach(): Unit = {
    //state.submitEvent(victoryEvent)
    //operationSequence = VictoryBehaviour().applyRule(state)
    //operationSequence.foreach(_.execute(state))
  }

  it should "check that the supposed winning DialogOperation has been returned" in {
    //val content = operationSequence.find(_.isInstanceOf[DialogOperation]).map(_.asInstanceOf[DialogOperation].content)
    //assert(content.isDefined && content.get.equals(winningDialogContent))
    pending
  }

  it should "check that itself has been consumed" in {
    //state.consumableBuffer should not contain dialogLaunchEvent
    pending
  }

}
