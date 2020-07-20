package model.entities.board


trait Disposition {
  def totalTiles: Int

  def rows: Int

  def columns: Int

  def tilePlacement(tileIndex: Int): (Int, Int)
}

object Disposition {

  private class SnakeDisposition(val totalTiles: Int, val ratio : Int = 1) extends Disposition {
    val side: Int = Math.sqrt(totalTiles).ceil.toInt

    override def rows: Int = side

    override def columns: Int = side

    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      val rowIndex = tileIndex / columns
      val columnIndex = tileIndex % columns
      (if (rowIndex % 2 == 0) columnIndex else columns - columnIndex - 1,
        rows - rowIndex - 1)
    }
  }

  private class SpiralDisposition(val totalTiles: Int, val ratio: Int = 1) extends Disposition {
    val side: Int = Math.sqrt(totalTiles).ceil.toInt

    override def rows: Int = side

    override def columns: Int = side

    private val borderLength = Math.max((columns + rows) * 2 - 4, 1)

    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      def _tilePlacement(tileIndex: Int, rows: Int, columns: Int): (Int, Int) = {
        if (tileIndex < borderLength)
          if (tileIndex < columns) (tileIndex, rows - 1) else
            if (tileIndex < columns + rows - 1) (columns - 1, rows + columns - tileIndex - 2) else
              if (tileIndex < columns * 2 + rows - 2) (rows + columns * 2 - tileIndex - 3, 0) else
            (0, tileIndex - rows - columns * 2 + 3)
        else
          _tilePlacement(tileIndex - borderLength, rows - 2, columns - 2) match {
            case (x, y) => (x + 1, y + 1)
          }
      }

      _tilePlacement(tileIndex, rows, columns)
    }
  }

  private class LoopDisposition(val totalTiles: Int) extends Disposition {

    var rows: Int = 0
    var columns: Int = 0

    //    if (totalTiles % 4 == 0) {
    //      rows = ((totalTiles + 4) / 4) + (totalTiles + 4) % 4
    //      columns = rows
    //    } else if (totalTiles % 2 == 0) {
    //      rows = 2
    //      columns = totalTiles / 2
    //    } else {
    //      rows = 2
    //      columns = (totalTiles + 1) / 2
    //    }
    //
    val side: Int = Math.sqrt(totalTiles).ceil.toInt
    rows = side
    columns = side

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

  def snake(total: Int): Disposition = new SnakeDisposition(total)

  def spiral(total: Int): Disposition = new SpiralDisposition(total)

  def loop(total: Int): Disposition = new LoopDisposition(total)

}
