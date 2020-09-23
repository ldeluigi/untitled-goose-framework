package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.UtilityWords.ValueWord
import untitled.goose.framework.model.events.Key

import scala.reflect.ClassTag

/** Used for "[property name] as [Type] value" */
case class KeyValueSetter(s: String) {

  /** Enables "[property name] as [Type] value" */
  def as[T: ClassTag](v: ValueWord): Key[T] = Key(s)
}
