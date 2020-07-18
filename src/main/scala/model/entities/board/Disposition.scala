package model.entities.board


trait Disposition {
  def totalTiles: Int

  def rows: Int

  def columns: Int

  def tilePlacement(tileIndex: Int): (Int, Int)
}

object Disposition {

  private class SnakeDisposition(val totalTiles: Int) extends Disposition {
    val side: Int = Math.sqrt(totalTiles).round.toInt

    override def rows: Int = side

    override def columns: Int = side

    override def tilePlacement(tileIndex: Int): (Int, Int) =
      (if ((tileIndex / columns) % 2 == 0) tileIndex % columns else columns - tileIndex % columns - 1,
        rows - tileIndex / columns - 1)
  }

  private class SpiralDisposition(val totalTiles: Int) extends Disposition {
    val side: Int = Math.sqrt(totalTiles).round.toInt

    override def rows: Int = side

    override def columns: Int = side


    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      val n: Int = totalTiles - tileIndex
      val k: Int = Math.ceil((Math.sqrt(n)) / 2).toInt
      val t: Int = 2 * k + 1
      val m: Int = t * t
      val m1: Int = m - t + 1
      val m2: Int = m1 - t + 1
      if (n >= m - t + 1) (k - (m - n), -k) else if (n >= m1 - t + 1) (-k, -k + (m1 - n)) else if (n >= m2 - t + 1) (-k + (m2 - n), k) else
        (k + 1 - (m2 - n - t), k + 1)
    }
  }

  private class LoopDisposition(val totalTiles: Int) extends Disposition {

    var rows: Int = 0
    var columns: Int = 0

    if (totalTiles % 4 == 0) {
      rows = ((totalTiles + 4) / 4) + (totalTiles + 4) % 4
      columns = rows
    } else if (totalTiles % 2 == 0) {
      rows = 2
      columns = totalTiles / 2
    } else {
      rows = 2
      columns = (totalTiles + 1) / 2
    }

    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      if (rows == 2) {
        new SnakeDisposition(totalTiles).tilePlacement(tileIndex)
      } else {
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
  }

  def snake(total: Int): Disposition = new SnakeDisposition(total)

  def spiral(total: Int): Disposition = new SpiralDisposition(total)

  def loop(total: Int): Disposition = new LoopDisposition(total)

}
