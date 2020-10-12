package untitled.goose.framework.model.entities.definitions

/** Represents the game path on the board. */
sealed trait OneWayPath[T] {

  /**
   * Returns the next item.
   *
   * @param curr the item of which to return the next one
   * @return the next item, if present
   */
  def next(curr: T): Option[T]

  /**
   * Returns the previous item.
   *
   * @param curr the item of which to return the previous one
   * @return the previous item, if present
   */
  def prev(curr: T): Option[T]

  /**
   * Returns the definition's first item.
   *
   * @return the first item.
   */
  def first: T
}

object OneWayPath {

  private case class OneWayPathImpl[E](first: E,
                                       _next: E => Option[E],
                                       _prev: E => Option[E]) extends OneWayPath[E] {
    override def next(curr: E): Option[E] = _next(curr)

    override def prev(curr: E): Option[E] = _prev(curr)
  }

  def apply[E](first: E, next: E => Option[E], prev: E => Option[E]): OneWayPath[E] =
    OneWayPathImpl(first, next, prev)
}
