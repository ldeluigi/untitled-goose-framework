package dsl.properties.board

import dsl.properties.board.BoardProperty._

trait BoardHasProperty {

  def has(prop: TileNumProperty): Unit = ???

  def has(prop: DispositionProperty): Unit = ???

  def has(properties: BoardProperty*): Unit = ???

}