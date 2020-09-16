package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.nodes.{EventDefinitionCollection, EventNode}
import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.events._

import scala.reflect.ClassTag

trait CustomEventInstance[PropertyInput] extends RuleBookNode {
  def properties: Map[Key[_], PropertyInput => Any]

  def generateEvent(input: PropertyInput): GameEvent

  def :+[T: ClassTag](name: String, value: PropertyInput => T): CustomEventInstance[PropertyInput]

  def name: String
}

object CustomEventInstance {

  private[dsl] abstract class AbstractCustomEventInstance[PropertyInput](override val name: String,
                                                                         definedEvents: EventDefinitionCollection)
    extends CustomEventInstance[PropertyInput] {

    var properties: Map[Key[_], PropertyInput => Any] = Map()

    def :+[T: ClassTag](name: String, value: PropertyInput => T): CustomEventInstance[PropertyInput] = {
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

    def initEvent(input: PropertyInput): CustomGameEvent

    override def check: Seq[String] = if (definedEvents.isEventDefined(name)) {
      val e: EventNode = definedEvents.getEvent(name)
      properties.keySet.diff(e.properties).map(p =>
        "Trying to set property \"" + p + "\" for customGameEvent named \"" + name + "\" but it was never defined"
      ).toSeq ++
        e.properties.diff(properties.keySet).map(p =>
          "Property \"" + p + "\" for customGameEvent named \"" + name + "\" was not set but was defined"
        ).toSeq
    }
    else Seq("\"" + name + "\" is not defined in " + definedEvents.name)
  }

}