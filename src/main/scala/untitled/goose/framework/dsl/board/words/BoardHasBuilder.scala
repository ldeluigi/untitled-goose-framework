package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.nodes.BoardBuilderNode
import untitled.goose.framework.dsl.board.words.BoardProperty.{DispositionProperty, TileNumProperty}
import untitled.goose.framework.model.entities.definitions.Disposition

/** Enables "board [verb] [property] ..." */
trait BoardHasBuilder {

  /** Enables "board has [tile number] ..." */
  def has(prop: TileNumProperty): Unit

  /** Enables "board has [disposition]" */
  def has(prop: DispositionProperty): Unit

  /** Enables "board has ([board property], [board property], ...)" */
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

  /** Creates a has-builder from a builder node. */
  def apply(builder: BoardBuilderNode): BoardHasBuilder = new BoardHasBuilderImpl(builder)

}