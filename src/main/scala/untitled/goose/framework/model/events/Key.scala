package untitled.goose.framework.model.events

import scala.reflect.ClassTag

case class Key[T: ClassTag](keyName: String) {

  val classTag: ClassTag[T] = implicitly

  def equals[O: ClassTag](other: Key[O]): Boolean = other match {
    case k: Key[O] => k.keyName == keyName && k.classTag.toString() == classTag.toString()
    case _ => false
  }

  override def hashCode(): Int = keyName.hashCode
}

