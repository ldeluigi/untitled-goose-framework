package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.model.events.Key

import scala.reflect.ClassTag

case class CustomEventInstance(name: String) {

  private var m: Map[Key[_], Any] = Map()

  def properties: Map[Key[_], Any] = m

  def +[T: ClassTag](prop: (String, T)): CustomEventInstance = {
    m += Key[T](prop._1) -> prop._2
    this
  }

  def :=[T: ClassTag](prop: (String, T)): CustomEventInstance = this + prop
}
