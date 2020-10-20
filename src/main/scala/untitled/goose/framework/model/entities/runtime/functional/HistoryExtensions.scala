package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.events.GameEvent

import scala.reflect.ClassTag

object HistoryExtensions {

  /** Pimps a history of events. */
  implicit class PimpedHistory[H <: GameEvent](history: Seq[H]) {
    /** A type alias for a history. */
    type History = Seq[H]

    /** Filters events different from the given one. */
    def excludeEvent(event: GameEvent): History = history.filterNot(_ == event)

    /** Filters events that are not in the first n of given type. */
    def skipOfType[T: ClassTag](n: Int = 1): History = {
      val exclude = history.only[T].take(n)
      history.filterNot(exclude.contains(_))
    }

    /** Filters events that are not of given type. */
    def excludeEventType[T: ClassTag](): History =
      history.filter({
        case _: T => false
        case _ => true
      })

    /** Filters only events of given turn. */
    def filterTurn(turn: Int): History = history.filter(_.turn == turn)

    /** Filters only events of given cycle. */
    def filterCycle(cycle: Int): History = history.filter(_.cycle == cycle)

    /** Filters only events of given name. */
    def filterName(name: String): History = history.filter(_.name == name)

    /** Filters only events that are of given type and converts the sequence to that
     * generic type.
     */
    def only[T](implicit classTag: ClassTag[T]): Seq[T] = history.filter({
      case _: T => true
      case _ => false
    }).map(_.asInstanceOf[T])
  }

}
