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
    customEvent.getProperty[Int]("intVal").get should be(1)
  }

  it should "break when asking for a property that was not set" in {
    a[NoSuchElementException] should be thrownBy customEvent.getProperty[Int]("intVal")
  }

  it should "break when asking for a defined property with the wrong type" in {
    customEvent.setProperty("intVal", 1)
    a[NoSuchElementException] should be thrownBy customEvent.getProperty[String]("intVal")
  }

  it should "work even if there are keys with same name but different values" in {
    customEvent.setProperty("value", 1)
    customEvent.setProperty("value", "ciao")

    customEvent.getProperty[Int]("value").get should be(1)
    customEvent.getProperty[String]("value").get should be("ciao")
  }


}
