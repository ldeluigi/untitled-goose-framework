package dsl.events.nodes

import dsl.events.words.EventType.EventType
import dsl.nodes.RuleBookNode
import model.events.{GameEvent, Key}

case class EventNode(name: String, eventType: EventType) extends RuleBookNode {

  override def check: Seq[String] = ???

  def addPropertyNode(propertyNode: EventPropertyNode): Unit = ???

  def isPropertyDefined[T](key: Key[T]): Boolean = ???

  def generateEvent: GameEvent = ???

}
