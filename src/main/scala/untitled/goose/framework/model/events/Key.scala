package untitled.goose.framework.model.events

import scala.reflect.ClassTag

// TODO scaladoc
class Key[T: ClassTag](val keyName: String) {

  val classTag: ClassTag[T] = implicitly

  override def equals(obj: Any): Boolean = obj match {
    case k: Key[_] => k.keyName == keyName && k.classTag == classTag
    case _ => false
  }

  override def hashCode(): Int = (keyName + classTag.toString()).hashCode

  override def toString: String = "Key: " + keyName + " Type: " + classTag.toString

}

object Key {
  def apply[T: ClassTag](keyName: String): Key[T] = new Key(keyName)
}
