package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.nodes.BoardBuilderNode
import untitled.goose.framework.dsl.board.words.properties.BoardProperty
import untitled.goose.framework.dsl.board.words.properties.BoardProperty.{DispositionProperty, TileNumProperty}
import untitled.goose.framework.model.entities.definitions.Disposition

trait BoardHasBuilder {

  def has(prop: TileNumProperty): Unit

  def has(prop: DispositionProperty): Unit

  def has(properties: BoardProperty*): Unit

}

object BoardHasBuilder {

  private class BoardHasBuilderImpl(boardBuilder: BoardBuilderNode) extends BoardHasBuilder {
    override def has(prop: TileNumProperty): Unit = boardBuilder.withNumberedTiles(prop.num)

    override def has(prop: DispositionProperty): Unit = boardBuilder.withDisposition(prop.dispositionType match {
      case DispositionType.Spiral => Disposition.spiral(_)
      case DispositionType.Ring => Disposition.ring(_)
      case DispositionType.Snake => Disposition.snake(_)
    })

    override def has(properties: BoardProperty*): Unit =
      properties foreach {
        case prop: TileNumProperty => has(prop)
        case prop: DispositionProperty => has(prop)
      }
  }

  def apply(builder: BoardBuilderNode): BoardHasBuilder = new BoardHasBuilderImpl(builder)

}