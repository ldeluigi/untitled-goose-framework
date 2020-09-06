package dsl.events.words

import dsl.events.nodes.EventNode
import model.events.Key

case class EventPropertiesWord(event: EventNode) {
  def having(properties: Key[_]*): Unit = ???
}

