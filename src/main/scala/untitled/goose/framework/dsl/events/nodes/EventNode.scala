package untitled.goose.framework.dsl.events.nodes

import untitled.goose.framework.dsl.events.words.EventType.EventType
import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.events.{GameEvent, Key}

case class EventNode(name: String, eventType: EventType) extends RuleBookNode {

  override def check: Seq[String] = ???

  def addPropertyNode(propertyNode: EventPropertyNode): Unit = ???

  def isPropertyDefined[T](key: Key[T]): Boolean = ???

  def generateEvent: GameEvent = ???

}
