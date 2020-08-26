package dsl.board.properties

trait BoardHasProperty {

  def has(prop: TileNumProperty): BoardPropertyChanger = ???

  def has(prop: DispositionProperty): BoardPropertyChanger = ???

}