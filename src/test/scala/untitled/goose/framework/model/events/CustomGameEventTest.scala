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
    customEvent.defineKey[Int]("intVal")
    customEvent.setProperty("intVal", 1)
    customEvent.getProperty[Int]("intVal").get should be(1)
  }

  it should "break when asking for a property that was not defined" in {
    a[NoSuchElementException] should be thrownBy customEvent.getProperty[Int]("intVal")
  }

  it should "break when setting a property that was not defined" in {
    an[IllegalArgumentException] should be thrownBy customEvent.setProperty("intVal", 1)
  }

  it should "break when asking for a defined property with the wrong type" in {
    customEvent.defineKey[Int]("intVal")
    customEvent.setProperty("intVal", 1)
    a[NoSuchElementException] should be thrownBy customEvent.getProperty[String]("intVal")
  }

  it should "break when setting a defined property with the wrong type" in {
    customEvent.defineKey[Int]("intVal")
    an[IllegalArgumentException] should be thrownBy customEvent.setProperty("intVal", "ciao")
  }


}
