package engine.core.vertx

import engine.`match`.Match
import engine.events.root.{GameEvent, NoOpEvent}
import model.entities.DialogContent
import model.entities.board.{Board, Disposition, Piece}
import model.rules.ruleset.PriorityRuleSet
import model.{MutableMatchState, Player}
import org.scalatest.concurrent.{Eventually, Waiters}
import org.scalatest.flatspec.{AnyFlatSpec, AsyncFlatSpec}
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import view.GooseController

import scala.concurrent.Future
import scala.math.abs

class GooseEngineTest extends AnyFlatSpec {

  behavior of "GooseEngineTest"

  val m: Match = Match(Board(5, Disposition.snake(5)), Map(Player("") -> Piece()), PriorityRuleSet())

  val cGenerator: (GameEvent => Unit) => GooseController = (onEvent: GameEvent => Unit) => new GooseController {
    override def update(state: MutableMatchState): Unit = {}

    override def showDialog(content: DialogContent): Future[GameEvent] = Future.successful(NoOpEvent)

    override def logEvent(event: GameEvent): Unit = onEvent(event)

    override def close(): Unit = {}
  }

  val e : GameEvent = new GameEvent {
    override def name: String = "???"

    override def turn: Int = -1

    override def isConsumed: Boolean = false

    override def consume(): Unit = {}

    override def groups: Set[String] = Set()
  }

  it should "stop" in {
    val prev: Int =
      Thread.currentThread().getThreadGroup.activeCount()
    val a: Array[Thread] = Array.ofDim(prev)
    Thread.currentThread().getThreadGroup.enumerate(a)
    val ge = GooseEngine(m, cGenerator(_ => {}))
    var now: Int = Thread.currentThread().getThreadGroup.activeCount()
    assert(now - prev > 2)
    ge.stop()
    Eventually.eventually(Waiters.timeout(5 seconds)) {
      now = Thread.currentThread().getThreadGroup.activeCount()
      val b: Array[Thread] = Array.ofDim(now)
      Thread.currentThread().getThreadGroup.enumerate(b)
      assert(abs(prev - now) <= 1)
    }
  }

  // TODO fix this test because wtf
//  it should "be a correct eventSink" in {
//    val promise = Promise[Assertion]
//    var ge: GooseEngine = null
//    ge = GooseEngine(m, cGenerator(ev => {
//      ge.stop()
//      if (e == ev)
//        promise.success(succeed)
//      else promise.failure(null)
//    }))
//    //ge.eventSink.accept(NoOpEvent)
//    ge.eventSink.accept(e)
//    promise.future
//  }

  it should "currentMatch" in {
    val ge = GooseEngine(m, cGenerator(_ => {}))
    assert(ge.currentMatch == m)
  }

}
