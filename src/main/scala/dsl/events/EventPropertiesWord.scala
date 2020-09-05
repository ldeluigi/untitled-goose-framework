package dsl.events

import scala.reflect.ClassTag

case class EventPropertiesWord(eventName: String) {
  def properties[T: ClassTag](props: String*): EventPropertiesWord = ???
}
