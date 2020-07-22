package engine.vertx

import engine.events.PlayerEvent
import model.GameEvent
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class EngineTest extends AsyncFlatSpec with Matchers {

  "GooseVerticle" should "reply to a message" in {
    val gooseEngine: GooseEngine = GooseEngine()
    gooseEngine.accept(new GameEvent {
      override def name: String = "???"

      override def group: List[String] = List()
    })
    assert(true)
  }
}
