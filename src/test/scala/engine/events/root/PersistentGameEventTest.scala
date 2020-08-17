package engine.events.root

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PersistentGameEventTest extends AnyFlatSpec with BeforeAndAfterEach with Matchers {

  var p: PersistentGameEvent = new PersistentGameEvent(0)

  override def beforeEach() {
    p = new PersistentGameEvent(5, Set("a", "b"))
  }

  behavior of "ConsumableGameEventTest"

  it should "expose groups" in {
    p.groups should equal(Set("a", "b"))
  }

  it should "not consume" in {
    p.consume()
    p.isConsumed should be(false)
  }

  it should "default to !isConsumed" in {
    p.isConsumed should be(false)
  }

  it should "expose name" in {
    p.name should equal("PersistentGameEvent")
  }

  it should "expose turn" in {
    p.turn should equal(5)
  }

  it should "check groups correctly" in {
    p.belongsTo("a") should be(true)
    p.belongsTo("b") should be(true)
    p.belongsTo("c") should be(false)
  }

}
