package dsl.board.properties

import dsl.board.properties.BoardProperty._

trait BoardHasProperty {

  def has(prop: TileNumProperty): Unit = ???

  def has(prop: DispositionProperty): Unit = ???

  def has(properties: BoardProperty*): Unit = ???

}