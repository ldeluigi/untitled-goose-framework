package untitled.goose.framework.controller.engine.vertx

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.controller.ViewController
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.definitions.{BoardDefinition, Disposition, GameDefinitionBuilder}
import untitled.goose.framework.model.entities.runtime.{Game, GameState, Piece, Player}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.special.NoOpEvent
import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType

import scala.collection.immutable.ListMap
import scala.concurrent.Future

class GooseEngineTest extends AnyFlatSpec with Matchers {

  behavior of "GooseEngineTest"

  val m: Game = Game(GameDefinitionBuilder()
    .board(BoardDefinition("test", 5, Disposition.snake(5)))
    .actionRules(Set())
    .behaviourRules(Seq())
    .cleanupRules(Seq())
    .playerOrderingType(PlayerOrderingType.FirstTurnRandomThenFixed)
    .playersRange(1 to 10)
    .build(), ListMap(Player("") -> Piece(Colour.Default.Blue)))

  def cGenerator(handler: GameEvent => Unit): ViewController = new ViewController {
    override def update(state: GameState): Unit = {}

    override def showDialog(content: DialogContent): Future[GameEvent] = Future.successful(NoOpEvent)

    override def close(): Unit = {}
  }

  val e: GameEvent = new GameEvent {
    override def name: String = "???"

    override def turn: Int = -1

    override def groups: Seq[String] = Seq()

    override def cycle: Int = 1
  }

  it should "check whether the engine matches a certain custom runtime" in {
    val ge = GooseEngine(m, cGenerator(_ => {}))
    ge.game should equal(m)
    ge.stop()
  }

}
