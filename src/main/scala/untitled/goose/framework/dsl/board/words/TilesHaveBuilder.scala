package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.board.nodes.{BoardBuilderNode, GraphicMapNode}
import untitled.goose.framework.dsl.board.words.properties.TileProperty.{BackgroundProperty, ColorProperty, GroupProperty}
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.definitions.TileIdentifier.Group
import untitled.goose.framework.view.GraphicDescriptor

trait TilesHaveBuilder {

  def have(group: GroupProperty): Unit

  def have(color: ColorProperty): Unit

  def have(background: BackgroundProperty): Unit

}

object TilesHaveBuilder {

  private class NumberedTilesHaveBuilder(numbers: Seq[Int], builder: BoardBuilderNode, graphicMap: GraphicMapNode) extends TilesHaveBuilder {
    override def have(group: GroupProperty): Unit =
      builder.withGroupedTiles(group.value, numbers: _*)

    override def have(color: ColorProperty): Unit =
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

    override def have(color: ColorProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(color.value))

    override def have(background: BackgroundProperty): Unit =
      graphicMap.addGraphicDescription(tileIdentifier, GraphicDescriptor(background.path))
  }

  def apply(numbers: Seq[Int], builder: BoardBuilderNode, graphicMap: GraphicMapNode): TilesHaveBuilder =
    new NumberedTilesHaveBuilder(numbers, builder, graphicMap)

  def apply(group: String, builder: BoardBuilderNode, graphicMap: GraphicMapNode): TilesHaveBuilder =
    new GroupHaveBuilder(TileIdentifier(Group(group)), builder, graphicMap)
}
