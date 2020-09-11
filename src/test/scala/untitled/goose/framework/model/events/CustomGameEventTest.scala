package untitled.goose.framework.model.events

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{convertToAnyShouldWrapper, _}

class CustomGameEventTest extends AnyFlatSpec with BeforeAndAfterEach {

  var customEvent: CustomGameEvent = CustomGameEvent(0, 0, "custom")

  behavior of "CustomGameEventTest"

  override def beforeEach(): Unit = {
    customEvent = CustomGameEvent(0, 0, "custom")
  }

  it should "set a custom property of the given type after defining the key" in {
    customEvent.setProperty("intVal", 1)
    customEvent.getProperty[Int]("intVal") should be(Some(1))
  }

  it should "return None for a property that was not set" in {
    customEvent.getProperty[Int]("intVal") should be(None)
  }

  it should "return None for a defined property with the wrong type" in {
    customEvent.setProperty("intVal", 1)
    customEvent.getProperty[String]("intVal") should be(None)
  }

  it should "work even if there are keys with same name but different values" in {
    customEvent.setProperty("value", 1)
    customEvent.setProperty("value", "ciao")

    customEvent.getProperty[Int]("value") should be(None)
    customEvent.getProperty[String]("value") should be(Some("ciao"))
  }


}
