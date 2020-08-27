package dsl.properties.tile

import dsl.nodes.GraphicMapNode
import dsl.properties.tile.TileProperty._
import model.TileIdentifier
import model.entities.board.BoardBuilder
import view.board.GraphicDescriptor

trait TileHasBuilder {
  def has(name: NameProperty): Unit

  def has(group: GroupProperty): Unit

  def has(color: ColorProperty): Unit

  def has(background: BackgroundProperty): Unit

  def has(properties: TileProperty*): Unit
}

object TileHasBuilder {

  private class TileHasBuilderImpl(tileIdentifier: TileIdentifier, builder: BoardBuilder, graphicMap: GraphicMapNode) extends TileHasBuilder {

    override def has(name: NameProperty): Unit =
      builder.withNamedTile(tileIdentifier.number.get, name.name)

    override def has(group: GroupProperty): Unit =
      builder.withGroupedTiles(group.value, tileIdentifier.number.get)

    override def has(color: ColorProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(color.value))

    override def has(background: BackgroundProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(background.path))

    override def has(properties: TileProperty*): Unit =
      properties.foreach {
        case prop: ColorProperty => has(prop)
        case prop: GroupProperty => has(prop)
        case prop: NameProperty => has(prop)
        case prop: BackgroundProperty => has(prop)
      }
  }

  def apply(number: Int, builder: BoardBuilder, graphicMap: GraphicMapNode): TileHasBuilder =
    new TileHasBuilderImpl(TileIdentifier(number), builder, graphicMap)

  def apply(name: String, builder: BoardBuilder, graphicMap: GraphicMapNode): TileHasBuilder =
    new TileHasBuilderImpl(TileIdentifier(name), builder, graphicMap)
}


