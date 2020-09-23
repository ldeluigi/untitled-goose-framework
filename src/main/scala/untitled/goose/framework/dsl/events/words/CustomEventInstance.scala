package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.nodes.{EventDefinitionCollection, EventNode}
import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.events._

import scala.reflect.ClassTag

/**
 * Defines a custom event instance with given name and properties.
 *
 * @tparam PropertyInput the data needed to compute property values.
 */
trait CustomEventInstance[PropertyInput] extends RuleBookNode {

  /** The properties defined for this custom event instance. */
  def properties: Map[Key[_], PropertyInput => Any]

  /** Generates the event with given information and data. */
  def generateEvent(input: PropertyInput): GameEvent

  /**
   * Appends a new value assignment to this custom event properties.
   *
   * @param name  name of the property.
   * @param value value assigned, computed from input.
   * @tparam T type of the value computed. Must conform to custom event definition.
   * @return this custom event instance.
   */
  def :+[T: ClassTag](name: String, value: PropertyInput => T): CustomEventInstance[PropertyInput]

  /** The name of this custom event. */
  def name: String
}

object CustomEventInstance {

  /**
   * Template class that leaves initEvent as the only unimplemented, abstract template method.
   *
   * @param name          the name of the custom event.
   * @param definedEvents a reference to the previously defined custom events.
   * @tparam PropertyInput the data needed to compute property values.
   */
  abstract class AbstractCustomEventInstance[PropertyInput](override val name: String,
                                                            definedEvents: EventDefinitionCollection)
    extends CustomEventInstance[PropertyInput] {

    override var properties: Map[Key[_], PropertyInput => Any] = Map()

    override def :+[T: ClassTag](name: String, value: PropertyInput => T): CustomEventInstance[PropertyInput] = {
      properties += Key[T](name) -> value
      this
    }

    override def generateEvent(input: PropertyInput): GameEvent = {
      val e: CustomGameEvent = initEvent(input)
      properties.foreach { prop =>
        e.setProperty(prop._1.keyName, prop._2(input))
      }
      e
    }

    /**
     * Template method. Should create a custom game event based on properties.
     * @param input the data available to create the custom event.
     * @return a new CustomGameEvent.
     */
    protected def initEvent(input: PropertyInput): CustomGameEvent

    override def check: Seq[String] = if (definedEvents.isEventDefined(name)) {
      val e: EventNode = definedEvents.getEvent(name)
      properties.keySet.diff(e.properties).map(p =>
        "Trying to set property \"" + p + "\" for customGameEvent named \"" + name + "\" but it was never defined"
      ).toSeq ++
        e.properties.diff(properties.keySet).map(p =>
          "Property \"" + p + "\" for customGameEvent named \"" + name + "\" was not set but was defined"
        ).toSeq
    } else Seq("\"" + name + "\" is not defined in " + definedEvents.name)
  }

}