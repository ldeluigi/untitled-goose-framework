package untitled.goose.framework.controller.engine.vertx

import untitled.goose.framework.controller.ViewController
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.special.NoOpEvent
import untitled.goose.framework.model.entities.runtime.{Game, GameState, Piece, Player, Position}
import untitled.goose.framework.model.rules.ruleset.{PlayerOrdering, PlayerOrderingType, PriorityRuleSet}
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.definitions.{BoardDefinition, Disposition, GameDefinitionBuilder}
import org.scalatest.concurrent.{Eventually, Waiters}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime

import scala.collection.immutable.ListMap
import scala.concurrent.Future
import scala.math.abs

class GooseEngineTest extends AnyFlatSpec with Matchers {

  behavior of "GooseEngineTest"

  val m: Game = Game(GameDefinitionBuilder()
    .board(BoardDefinition("test", 5, Disposition.snake(5)))
    .actionRules(Set())
    .behaviourRules(Seq())
    .cleanupRules(Seq())
    .playerOrderingType(PlayerOrderingType.FirstTurnRandomThenFixed)
    .playersRange(1 to 10)
    .build, ListMap(Player("") -> Piece(Colour.Blue)))

  def cGenerator(handler: GameEvent => Unit): ViewController = new ViewController {
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

  it should "check whether the engine matches a certain custom runtime" in {
    val ge = GooseEngine(m, cGenerator(_ => {}))
    ge.game should equal(m)
    ge.stop()
  }

}
