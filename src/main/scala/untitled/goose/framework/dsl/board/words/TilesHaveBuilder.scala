package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.nodes.{BoardBuilderNode, GraphicMapNode}
import untitled.goose.framework.dsl.board.words.TileProperty.{BackgroundProperty, ColourProperty, GroupProperty}
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.definitions.TileIdentifier.Group
import untitled.goose.framework.view.GraphicDescriptor

/** Used for "tiles [verb] ..." */
trait TilesHaveBuilder {

  /** Enables "tiles have [group] ..." */
  def have(group: GroupProperty): Unit

  /** Enables "tiles have [colour] ..." */
  def have(color: ColourProperty): Unit

  /** Enables "tiles have [background] ..." */
  def have(background: BackgroundProperty): Unit

}

object TilesHaveBuilder {

  private class NumberedTilesHaveBuilder(numbers: Seq[Int], builder: BoardBuilderNode, graphicMap: GraphicMapNode) extends TilesHaveBuilder {
    override def have(group: GroupProperty): Unit =
      builder.withGroupedTiles(group.value, numbers: _*)

    override def have(color: ColourProperty): Unit =
      numbers.foreach(n => {
        graphicMap.addGraphicDescription(TileIdentifier(n), GraphicDescriptor(color.value))
      })

    override def have(background: BackgroundProperty): Unit = numbers.foreach(n => {
      graphicMap.addGraphicDescription(TileIdentifier(n), GraphicDescriptor(background.path))
    })
  }

  private class GroupHaveBuilder(tileIdentifier: TileIdentifier, builder: BoardBuilderNode, graphicMap: GraphicMapNode) extends TilesHaveBuilder {
    override def have(group: GroupProperty): Unit =
      builder.withGroupedTiles(tileIdentifier.group.get, group.value)

    override def have(color: ColourProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(color.value))

    override def have(background: BackgroundProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(background.path))
  }

  /**
   * Creates a TilesHaveBuilder for given tiles.
   *
   * @param numbers    Identifies the tiles by number.
   * @param builder    The board builder that collects tile data.
   * @param graphicMap The collection of tile graphic components.
   * @return A new TilesHaveBuilder.
   */
  def apply(numbers: Seq[Int], builder: BoardBuilderNode, graphicMap: GraphicMapNode): TilesHaveBuilder =
    new NumberedTilesHaveBuilder(numbers, builder, graphicMap)

  /**
   * Creates a TilesHaveBuilder for given tiles.
   *
   * @param group      Identifies the tiles by group.
   * @param builder    The board builder that collects tile data.
   * @param graphicMap The collection of tile graphic components.
   * @return A new TilesHaveBuilder.
   */
  def apply(group: String, builder: BoardBuilderNode, graphicMap: GraphicMapNode): TilesHaveBuilder =
    new GroupHaveBuilder(TileIdentifier(Group(group)), builder, graphicMap)
}
