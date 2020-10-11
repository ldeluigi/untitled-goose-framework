package untitled.goose.framework.model.entities.definitions

trait OneWayPath[T] {

  /** Returns the next tile.
   *
   * @param tile the tile of which to return the next one
   * @return the next tile, if present
   */
  def next(tile: T): Option[T]

  /** Returns the previous tile.
   *
   * @param tile the tile of which to return the previous one
   * @return the previous tile, if present
   */
  def prev(tile: T): Option[T]

  /** Returns the definition's first tile.
   *
   * @return the first tile.
   */
  def first: T
}
