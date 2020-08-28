package model.rules.behaviours

import engine.events.consumable.StepMovementEvent
import mock.MatchMock
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MultipleStepBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "MultipleStepBehaviourTest"

  it should "applyRule" in {
    val game: Game = MatchMock.default
    val event = StepMovementEvent(1, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
    val state: MutableGameState = game.currentState

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = MultipleStepBehaviour().applyRule(state) // punto 3
    operationSequence.foreach(_.execute(state)) //punto 4 eseguo tutte le op
    // guardo risultato di StepOperation, poi come queste cose alterano lo stato e verificare che ci siano
    // in mock i giocatori sono in posizione nulla, quindi non lanciano l'evento
    // controllare che il player si trovi nella tile di prima + 1
    // evento entrato sulla tile, evento fermato sulla tile
    // più condizioni in uno stesso test


    // verificare modifica corretta di game state

    //MultipleStepBehaviour().applyRule(m.currentState) should have size 0


  }

}