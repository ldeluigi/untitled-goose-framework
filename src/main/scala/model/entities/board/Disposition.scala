package model.entities.board


trait Disposition {
  def totalTiles: Int

  def rows: Int

  def columns: Int

  def tilePlacement(tileIndex: Int): (Int, Int)
}

object Disposition {

  private abstract class BaseDisposition(val totalTiles: Int, val ratio: Int) extends Disposition {
    private val height: Double = Math.sqrt(totalTiles / ratio)

    override def rows: Int = height.ceil.toInt

    override def columns: Int = (totalTiles / height).ceil.toInt
  }

  private class SnakeDisposition(totalTiles: Int, ratio: Int) extends BaseDisposition(totalTiles, ratio) {
    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      val rowIndex = tileIndex / columns
      val columnIndex = tileIndex % columns
      (if (rowIndex % 2 == 0) columnIndex else columns - columnIndex - 1,
        rows - rowIndex - 1)
    }
  }

  // TODO fix for input = 25
  private class SpiralDisposition(totalTiles: Int, ratio: Int) extends BaseDisposition(totalTiles, ratio) {

    private val borderLength = Math.max((columns + rows) * 2 - 4, 1)

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

  // TODO fix
  private class LoopDisposition(val totalTiles: Int) extends Disposition {

    private val side: Int = Math.sqrt(totalTiles).ceil.toInt

    override val rows: Int = side
    override val columns: Int = side

    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      val c = columns - 1
      val r = rows - 1
      val i = tileIndex
      if (i < c) {
        (i % c, 0)
      } else if (i < c + r) {
        (c, i % r)
      } else if (i < 2 * c + r) {
        (c - (i % c), r)
      } else {
        (0, r - (i % r))
      }
    }
  }

  def snake(total: Int, ratio: Int = 1): Disposition = new SnakeDisposition(total, ratio)

  def spiral(total: Int, ratio: Int = 1): Disposition = new SpiralDisposition(total, ratio)

  def loop(total: Int): Disposition = new LoopDisposition(total)

}
