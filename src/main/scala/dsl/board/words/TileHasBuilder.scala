package dsl.board.words

import dsl.board.nodes.{BoardBuilderNode, GraphicMapNode}
import dsl.board.words.properties.TileProperty
import dsl.board.words.properties.TileProperty.{BackgroundProperty, ColorProperty, GroupProperty, NameProperty}
import model.TileIdentifier
import view.scalafx.board.GraphicDescriptor

trait TileHasBuilder {
  def has(name: NameProperty): Unit

  def has(group: GroupProperty): Unit

  def has(color: ColorProperty): Unit

  def has(background: BackgroundProperty): Unit

  def has(properties: TileProperty*): Unit
}

object TileHasBuilder {

  private class TileHasBuilderImpl(tileIdentifier: TileIdentifier, builder: BoardBuilderNode, graphicMap: GraphicMapNode) extends TileHasBuilder {

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

  def apply(number: Int, builder: BoardBuilderNode, graphicMap: GraphicMapNode): TileHasBuilder =
    new TileHasBuilderImpl(TileIdentifier(number), builder, graphicMap)

  def apply(name: String, builder: BoardBuilderNode, graphicMap: GraphicMapNode): TileHasBuilder =
    new TileHasBuilderImpl(TileIdentifier(name), builder, graphicMap)
}

