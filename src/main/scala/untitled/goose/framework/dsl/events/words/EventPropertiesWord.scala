package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.nodes.EventNode
import untitled.goose.framework.model.events.Key

/** Used for "event having [property]" */
case class EventPropertiesWord(event: EventNode) {

  /** Enables "event having ([property], [property], ...)" */
  def having(properties: Key[_]*): Unit = properties foreach {
    event.defineProperty(_)
  }
}

