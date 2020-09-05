package dsl.events

import model.events.Key

case class EventPropertiesWord(eventName: String) {
  def having(properties: Key[_]*): Unit = ???
}

