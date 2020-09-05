package model.events

import scala.reflect.ClassTag

case class Key[T: ClassTag](keyName: String) {

  override def equals(obj: Any): Boolean = obj match {
    case k: Key[T] => k.keyName == keyName
    case _ => false
  }

  override def hashCode(): Int = keyName.hashCode
}

