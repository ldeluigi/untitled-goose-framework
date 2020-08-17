package engine.events.root

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConsumableGameEventTest extends AnyFlatSpec with BeforeAndAfterEach with Matchers {

  var c: ConsumableGameEvent = new ConsumableGameEvent(0)

  override def beforeEach() {
    c = new ConsumableGameEvent(5, Set("a", "b"))
  }

  behavior of "ConsumableGameEventTest"

  it should "expose groups" in {
    c.groups should equal(Set("a", "b"))
  }

  it should "consume" in {
    c.consume()
    c.isConsumed should be(true)
  }

  it should "default to !isConsumed" in {
    c.isConsumed should be(false)
  }

  it should "expose name" in {
    c.name should equal("ConsumableGameEvent")
  }

  it should "expose turn" in {
    c.turn should equal(5)
  }

  it should "check groups correctly" in {
    c.belongsTo("a") should be(true)
    c.belongsTo("b") should be(true)
    c.belongsTo("c") should be(false)
  }

}
