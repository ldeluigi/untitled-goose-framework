package model.entities.definitions

trait BoardBuilder {
  /** Specifies a board's name.
   *
   * @param name the name of the board
   * @return the partial board builder object
   */
  def withName(name: String): BoardBuilder

  /** Specifies the board's tile numeration.
   *
   * @param total the global number of tiles in the runtime
   * @param from  the initial tile index
   * @return the partial board builder object
   */
  def withNumberedTiles(total: Int, from: Int = 1): BoardBuilder

  /** Specifies the tile's name
   *
   * @param number the tile's index to name
   * @param name   the name to set to the specified tile
   * @return the partial board builder object
   */
  def withNamedTile(number: Int, name: String): BoardBuilder

  def withGroupedTiles(group: String, number: Int*): BoardBuilder

  def withGroupedTiles(originGroup: String, newGroup: String): BoardBuilder

  /** Specifies the board's disposition.
   *
   * @param disposition a certain disposition type
   * @return the partial board builder object
   */
  def withDisposition(disposition: Int => Disposition): BoardBuilder

  /** Completes the board's building process.
   *
   * @return the parameter-complete board
   */
  def complete(): Board

  def isCompletable: Boolean
}
