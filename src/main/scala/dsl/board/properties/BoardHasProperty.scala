package dsl.board.properties

trait BoardHasProperty {

  def has(prop: TileNumProperty): BoardPropertyChanger

  def has(prop: DispositionProperty): BoardPropertyChanger

}


object BoardHasProperty {

  private class BoardHasPropertyImpl extends BoardHasProperty {
    override def has(prop: TileNumProperty): BoardPropertyChanger = ???

    override def has(prop: DispositionProperty): BoardPropertyChanger = ???
  }

  def apply(): BoardHasProperty = new BoardHasPropertyImpl()

}
