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


  private class LoopDisposition(val totalTiles: Int, ratio: Int) extends Disposition {

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

  def snake(total: Int, ratio: Int = 1): Disposition = new SnakeDisposition(total, ratio)

  def spiral(total: Int, ratio: Int = 1): Disposition = new SpiralDisposition(total, ratio)

  def loop(total: Int, ratio: Int = 1): Disposition = new LoopDisposition(total, ratio)

}
