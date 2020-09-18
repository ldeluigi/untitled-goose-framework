package untitled.goose.framework.model.events

import scala.reflect.ClassTag

/**
 * A Key stores information about a property name and its type at runtime,
 * using scala reflections (the ClassTag).
 *
 * @param keyName the property name.
 * @tparam T the type of the property key.
 */
class Key[T: ClassTag](val keyName: String) {

  /** Exposes the ClassTag stored for type [[T]]. */
  val classTag: ClassTag[T] = implicitly

  override def equals(obj: Any): Boolean = obj match {
    case k: Key[_] => k.keyName == keyName && k.classTag == classTag
    case _ => false
  }

  override def hashCode(): Int = (keyName + classTag.toString()).hashCode

  override def toString: String = "Key: " + keyName + " Type: " + classTag.toString

}

object Key {

  /** Factory method that creates a Key for a property with value of type [[T]]. */
  def apply[T: ClassTag](keyName: String): Key[T] = new Key(keyName)
}
