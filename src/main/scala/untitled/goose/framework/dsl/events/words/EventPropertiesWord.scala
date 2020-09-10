package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.nodes.EventNode
import untitled.goose.framework.model.events.Key

case class EventPropertiesWord(event: EventNode) {
  def having(properties: Key[_]*): Unit = properties foreach {
    event defineProperty _
  }
}

