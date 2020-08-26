package dsl.properties.board

import dsl.properties.board.BoardProperty._
import model.entities.board.BoardBuilder

trait BoardHasBuilder {

  def has(prop: TileNumProperty): Unit

  def has(prop: DispositionProperty): Unit = ???

  def has(properties: BoardProperty*): Unit = ???

}

object BoardHasBuilder {

  private class BoardHasBuilderImpl(boardBuilder: BoardBuilder) extends BoardHasBuilder {
    override def has(prop: TileNumProperty): Unit = ???
  }

  def apply(): BoardHasBuilder = new BoardHasBuilderImpl(new BoardBuilder)

}