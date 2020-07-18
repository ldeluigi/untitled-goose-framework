package model.entities.board

import model.entities.board.DispositionType.DispositionType

object DispositionType extends Enumeration {
  type DispositionType = Value
  val Snake, Spiral, Loop = Value
}

trait Disposition {
  def totalTiles: Int

  def rows: Int

  def columns: Int

  def tilePlacement(tileIndex: Int): (Int, Int)
}

object Disposition {

  private class SnakeDisposition(val totalTiles: Int) extends Disposition {
    override def rows: Int = ???

    override def columns: Int = ???

    override def tilePlacement(tileIndex: Int): (Int, Int) = ???
  }

  private class SpiralDisposition(val totalTiles: Int) extends Disposition {
    override def rows: Int = ???

    override def columns: Int = ???

    override def tilePlacement(tileIndex: Int): (Int, Int) = ???
  }

  private class LoopDisposition(val totalTiles: Int) extends Disposition {

    var rows: Int = 0
    var columns: Int = 0

    if (totalTiles % 4 == 0) {
      rows = ((totalTiles + 4) / 4) + (totalTiles + 4) % 4
      columns = rows
    } else if (totalTiles % 2 == 0) {
      rows = totalTiles / 2
      columns = 2
    } else {
      rows = (totalTiles + 1) / 2
      columns = 2
    }

    override def tilePlacement(tileIndex: Int): (Int, Int) = {
      ???
    }
  }

  def apply(totalTiles: Int, dispositionType: DispositionType): Disposition = dispositionType match {
    case DispositionType.Snake => new SnakeDisposition(totalTiles)
    case DispositionType.Spiral => new SpiralDisposition(totalTiles)
    case DispositionType.Loop => new LoopDisposition(totalTiles)
  }
}
