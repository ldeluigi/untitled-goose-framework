package untitled.goose.framework.dsl.events.words

import scala.reflect.ClassTag

case class CustomEventInstance(name: String) {

  def +[T: ClassTag](prop: (String, T)): CustomEventInstance = {
    ??? //TODO manage this property in a collection
    this
  }

  def :=[T: ClassTag](prop: (String, T)): CustomEventInstance = this + prop
}
