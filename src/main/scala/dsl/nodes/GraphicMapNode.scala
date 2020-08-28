package dsl.nodes

import model.TileIdentifier
import view.board.GraphicDescriptor

class GraphicMapNode(builderNode: BoardBuilderNode) extends RuleBookNode {

  private var graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map()

  def map: Map[TileIdentifier, GraphicDescriptor] = graphicMap

  def addGraphicDescription(tileIdentifier: TileIdentifier, graphicDescriptor: GraphicDescriptor): Unit =
    graphicMap = graphicMap + (tileIdentifier -> graphicDescriptor)

  override def check: Seq[String] = {
    var seq = graphicMap.keys
      .filter(_.number.isDefined)
      .map(_.number.get)
      .filter(!builderNode.definedTiles.contains(_))
      .map("Tile " + _ + " define style but is not valid in this board")
      .toSeq
    seq ++= graphicMap.keys
      .filter(_.name.isDefined)
      .map(_.name.get)
      .filter(!builderNode.nameIsDefined(_))
      .map(_ + " name define style but is not assigned to any tile")
      .toSeq
    seq ++= graphicMap.keys
      .filter(_.group.isDefined)
      .map(_.group.get.groupName)
      .filter(!builderNode.groupIsDefined(_))
      .map(_ + " group define style but is not assigned to any tile").toSeq
    seq
  }
}
