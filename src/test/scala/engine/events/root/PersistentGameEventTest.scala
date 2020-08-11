package engine.events.root

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec

class PersistentGameEventTest extends AnyFlatSpec with BeforeAndAfterEach {

  var p: PersistentGameEvent = new PersistentGameEvent(0)

  override def beforeEach() {
    p = new PersistentGameEvent(5, Set("a", "b"))
  }

  behavior of "ConsumableGameEventTest"

  it should "expose groups" in {
    assert(p.groups == Set("a", "b"))
  }

  it should "not consume" in {
    p.consume()
    assert(!p.isConsumed)
  }

  it should "default to !isConsumed" in {
    assert(!p.isConsumed)
  }

  it should "expose name" in {
    assert(p.name == "PersistentGameEvent")
  }

  it should "expose turn" in {
    assert(p.turn == 5)
  }

  it should "check groups correctly" in {
    assert(p.belongsTo("a"))
    assert(p.belongsTo("b"))
    assert(!p.belongsTo("c"))
  }

}
