package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.nodes.{BoardBuilderNode, GraphicMapNode}
import untitled.goose.framework.dsl.board.words.TileProperty.{BackgroundProperty, ColourProperty, GroupProperty, NameProperty}
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.view.GraphicDescriptor

/** Used for "tile [verb] ..." */
trait TileHasBuilder {

  /** Enables "tile has [name] ..." */
  def has(name: NameProperty): Unit

  /** Enables "tile has [group] ..." */
  def has(group: GroupProperty): Unit

  /** Enables "tile has [colour] ..." */
  def has(colour: ColourProperty): Unit

  /** Enables "tile has [background image] ..." */
  def has(background: BackgroundProperty): Unit

  /** Enables "tile has ([property], [property], ..." */
  def has(properties: TileProperty*): Unit
}

object TileHasBuilder {

  private class TileHasBuilderImpl(tileIdentifier: TileIdentifier, builder: BoardBuilderNode, graphicMap: GraphicMapNode) extends TileHasBuilder {

    override def has(name: NameProperty): Unit =
      builder.withNamedTile(tileIdentifier.number.get, name.name)

    override def has(group: GroupProperty): Unit =
      builder.withGroupedTiles(group.value, tileIdentifier.number.get)

    override def has(colour: ColourProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(colour.value))

    override def has(background: BackgroundProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(background.path))

    override def has(properties: TileProperty*): Unit =
      properties.foreach {
        case prop: ColourProperty => has(prop)
        case prop: GroupProperty => has(prop)
        case prop: NameProperty => has(prop)
        case prop: BackgroundProperty => has(prop)
      }
  }

  /**
   * Creates a TileHasBuilder for given tile.
   *
   * @param number     Identifies the tile by number.
   * @param builder    The board builder that collects tile data.
   * @param graphicMap The collection of tile graphic components.
   * @return a new TileHasBuilder.
   */
  def apply(number: Int, builder: BoardBuilderNode, graphicMap: GraphicMapNode): TileHasBuilder =
    new TileHasBuilderImpl(TileIdentifier(number), builder, graphicMap)

  /**
   * Creates a TileHasBuilder for given tile.
   *
   * @param name       Identifies the tile by name.
   * @param builder    The board builder that collects tile data.
   * @param graphicMap The collection of tile graphic components.
   * @return a new TileHasBuilder.
   */
  def apply(name: String, builder: BoardBuilderNode, graphicMap: GraphicMapNode): TileHasBuilder =
    new TileHasBuilderImpl(TileIdentifier(name), builder, graphicMap)
}


