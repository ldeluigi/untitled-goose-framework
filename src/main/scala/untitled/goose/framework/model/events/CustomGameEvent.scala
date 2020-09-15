package untitled.goose.framework.model.events

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

/**
 * CustomGameEvent is a ConsumableGameEvent that represent a generic event
 * customized by a player and that is consumed after a cycle.
 *
 * @param turn  The turn at which the event occurred.
 * @param cycle The cycle at which the event is resolved.
 * @param name  The name specified for the event.
 */
class CustomGameEvent(val turn: Int,
                      val cycle: Int,
                      override val name: String)
  extends ConsumableGameEvent {
  private var propertyList: Map[String, Any] = Map()

  override def toString: String = super.toString + " Properties: " + propertyList

  /**
   * Retrieve a property of the type T bound to the given key.
   * Passo la stringa che identifica il nome della prorpietà e il parametro il tipo, ritorna solo se esiste una chiave per quella cosa che è di quel tipo.
   *
   * @param key The key mantain a property related of the given type T.(coppia chiave valore, la chiave mantiene il tipo ed identifica una proprietà)
   * @tparam T Specify the type T of the param.
   * @return An option of type T containing the value of the property if existing or None otherwhise.
   */
  def getProperty[T: ClassTag](key: String): Option[T] =
    propertyList.get(key).flatMap({
      case x: T => Some(x)
      case _ => None
    })

  /**
   * Set the value of the property based on a given key.
   *
   * @param key   A key, that maintain a property related of the given type T.
   * @param value The value of the property.
   */
  def setProperty(key: String, value: Any): Unit = propertyList += key -> value

}

object CustomGameEvent {
  /**
   * This factory creates a CustomGameEvent based on the generic GameEvent.
   *
   * @param turn  The turn at which the event occurred.
   * @param cycle The cycle at which the event is resolved.
   * @param name  The name specified for the event.
   * @return A new CustomGameEvent event.
   */
  def apply(turn: Int, cycle: Int, name: String): CustomGameEvent = new CustomGameEvent(turn, cycle, name)
}
