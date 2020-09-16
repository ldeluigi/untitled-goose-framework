package untitled.goose.framework.model.entities.definitions

/** The strategy used to place the tiles in the 2D space of the definition. */
trait Disposition {

  /** Total number of tiles. */
  def totalTiles: Int

  /** Number of tile rows. */
  def rows: Int

  /** Number of tile columns. */
  def columns: Int

  /**
   * Defines where a tile should be placed on the table to compose the definition.
   *
   * @param tileIndex the index of the tile.
   * @return the tile's coordinates.
   */
  def tilePlacement(tileIndex: Int): (Int, Int)

  def ==(obj: Disposition): Boolean =
    rows == obj.rows &&
      columns == obj.columns &&
      totalTiles == obj.totalTiles &&
      Range(0, totalTiles)
        .map(i => tilePlacement(i) == obj.tilePlacement(i))
        .reduce(_ && _)

  override def equals(obj: Any): Boolean = obj match {
    case x: Disposition => x == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName + " (" + totalTiles + " in " + columns + "x" + rows + ")"
}

object Disposition {

  private abstract class BaseDisposition(val totalTiles: Int, val ratio: Int) extends Disposition {
    private val height: Double = Math.sqrt(totalTiles / ratio)

    override val rows: Int = Math.max(height.ceil.toInt, 1)

    override val columns: Int = Math.max((totalTiles / height).ceil.toInt, 1)
  }

  private class SnakeDisposition(totalTiles: Int, ratio: Int) extends BaseDisposition(totalTiles, ratio) {
    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      val rowIndex = tileIndex / columns
      val columnIndex = tileIndex % columns
      (if (rowIndex % 2 == 0) columnIndex else columns - columnIndex - 1,
        rows - rowIndex - 1)
    }
  }

  private class SpiralDisposition(totalTiles: Int, ratio: Int) extends BaseDisposition(totalTiles, ratio) {

    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      def _tilePlacement(tileIndex: Int, rows: Int, columns: Int): (Int, Int) = {
        val borderLength = Math.max((columns + rows) * 2 - 4, 1)
        if (tileIndex < borderLength)
          if (tileIndex < columns)
            (tileIndex, rows - 1)
          else if (tileIndex < columns + rows - 1)
            (columns - 1, rows + columns - tileIndex - 2)
          else if (tileIndex < columns * 2 + rows - 2)
            (rows + columns * 2 - tileIndex - 3, 0)
          else (0, tileIndex - rows - columns * 2 + 3)
        else
          _tilePlacement(tileIndex - borderLength, rows - 2, columns - 2) match {
            case (x, y) => (x + 1, y + 1)
          }
      }

      _tilePlacement(tileIndex, rows, columns)
    }
  }

  private class RingDisposition(val totalTiles: Int, ratio: Int) extends Disposition {

    private val rowsPlusColumns: Int = (totalTiles + totalTiles % 2) / 2 + 2

    override val rows: Int = Math.max(rowsPlusColumns / (ratio + 1), 1)

    override val columns: Int = Math.max(rowsPlusColumns - rows, 1)

    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      if (tileIndex < columns)
        (tileIndex, rows - 1)
      else if (tileIndex < columns + rows - 1)
        (columns - 1, rows + columns - tileIndex - 2)
      else if (tileIndex < columns * 2 + rows - 2)
        (rows + columns * 2 - tileIndex - 3, 0)
      else (0, tileIndex - rows - columns * 2 + 3)
    }
  }

  /** A factory that creates a new snake type tiles disposition. */
  def snake(total: Int, ratio: Int = 1): Disposition = new SnakeDisposition(total, ratio)

  /** A factory that creates a new spiral type tiles disposition. */
  def spiral(total: Int, ratio: Int = 1): Disposition = new SpiralDisposition(total, ratio)

  /** A factory that creates a new ring type tiles disposition. */
  def ring(total: Int, ratio: Int = 1): Disposition = new RingDisposition(total, ratio)

  /** Implicit conversion to a function. */
  implicit def toFunction(disposition: Disposition): Int => (Int, Int) = disposition.tilePlacement

}
