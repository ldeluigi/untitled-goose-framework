package engine.core.vertx

import engine.events.GameEvent
import engine.events.special.NoOpEvent
import model.entities.DialogContent
import model.entities.board.{Board, Disposition, Piece, Position}
import model.game.{Game, GameState}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet}
import model.{Color, Player}
import org.scalatest.concurrent.{Eventually, Waiters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import view.GooseController

import scala.concurrent.Future
import scala.math.abs

class GooseEngineTest extends AnyFlatSpec with Matchers {

  behavior of "GooseEngineTest"

  val m: Game = Game(Board(5, Disposition.snake(5)), Map(Player("") -> Piece(Color.Blue)), PriorityRuleSet(
    tiles => Position(tiles.toList.sorted.take(1).head),
    PlayerOrdering.randomOrder(7),
    1 to 10,
    Set(),
    Seq(),
    Seq())
  )

  def cGenerator(handler: GameEvent => Unit): GooseController = new GooseController {
    override def update(state: GameState): Unit = {}

    override def showDialog(content: DialogContent): Future[GameEvent] = Future.successful(NoOpEvent)

    override def logAsyncEvent(event: GameEvent): Unit = handler(event)

    override def close(): Unit = {}
  }

  val e: GameEvent = new GameEvent {
    override def name: String = "???"

    override def turn: Int = -1

    override def groups: Set[String] = Set()

    override def cycle: Int = 1
  }

  it should "stop the engine in a reasonable amount of time " in {
    val prev: Int =
      Thread.currentThread().getThreadGroup.activeCount()
    val a: Array[Thread] = Array.ofDim(prev)
    Thread.currentThread().getThreadGroup.enumerate(a)
    val ge = GooseEngine(m, cGenerator(_ => {}))
    var now: Int = Thread.currentThread().getThreadGroup.activeCount()
    // Non deterministic: now - prev should be >= 2
    ge.stop()
    Eventually.eventually(Waiters.timeout(5 seconds)) {
      now = Thread.currentThread().getThreadGroup.activeCount()
      val b: Array[Thread] = Array.ofDim(now)
      Thread.currentThread().getThreadGroup.enumerate(b)
      abs(prev - now) should be <= 1
    }
  }

  it should "check whether the engine matches a certain custom game" in {
    val ge = GooseEngine(m, cGenerator(_ => {}))
    ge.currentMatch should equal(m)
    ge.stop()
  }

}
