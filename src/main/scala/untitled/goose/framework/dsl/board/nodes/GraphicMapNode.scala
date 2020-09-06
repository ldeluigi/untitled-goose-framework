package untitled.goose.framework.dsl.board.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.TileIdentifier
import untitled.goose.framework.view.scalafx.board.GraphicDescriptor

class GraphicMapNode(identifiers: TileIdentifiersCollection) extends RuleBookNode {

  private var graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map()

  def map: Map[TileIdentifier, GraphicDescriptor] = graphicMap

  def addGraphicDescription(tileIdentifier: TileIdentifier, graphicDescriptor: GraphicDescriptor): Unit =
    graphicMap = graphicMap + (tileIdentifier -> graphicDescriptor)

  override def check: Seq[String] = {
    var seq = graphicMap.keys
      .filter(_.number.isDefined)
      .map(_.number.get)
      .filter(!identifiers.containsNumber(_))
      .map("Tile " + _ + " define style but is not valid in this board")
      .toSeq
    seq ++= graphicMap.keys
      .filter(_.name.isDefined)
      .map(_.name.get)
      .filter(!identifiers.containsName(_))
      .map(_ + " name define style but is not assigned to any tile")
      .toSeq
    seq ++= graphicMap.keys
      .filter(_.group.isDefined)
      .map(_.group.get.groupName)
      .filter(!identifiers.containsGroup(_))
      .map(_ + " group define style but is not assigned to any tile").toSeq
    seq
  }
}
