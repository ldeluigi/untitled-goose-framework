package engine.events.root

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec

class ConsumableGameEventTest extends AnyFlatSpec with BeforeAndAfterEach {

  var c: ConsumableGameEvent = new ConsumableGameEvent(0)

  override def beforeEach() {
    c = new ConsumableGameEvent(5, Set("a", "b"))
  }

  behavior of "ConsumableGameEventTest"

  it should "expose groups" in {
    assert(c.groups == Set("a", "b"))
  }

  it should "consume" in {
    c.consume()
    assert(c.isConsumed)
  }

  it should "default to !isConsumed" in {
    assert(!c.isConsumed)
  }

  it should "expose name" in {
    assert(c.name == "ConsumableGameEvent")
  }

  it should "expose turn" in {
    assert(c.turn == 5)
  }

  it should "check groups correctly" in {
    assert(c.belongsTo("a"))
    assert(c.belongsTo("b"))
    assert(!c.belongsTo("c"))
  }

}
