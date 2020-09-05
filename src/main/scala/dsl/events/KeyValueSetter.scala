package dsl.events

import dsl.words.event.ValueWord
import model.events.Key

import scala.reflect.ClassTag

case class KeyValueSetter(s: String) {
  def as[T: ClassTag](v: ValueWord): Key[T] = Key(s)
}
