package untitled.goose.framework.dsl.board.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.GraphicDescriptor
import untitled.goose.framework.model.entities.definitions.TileIdentifier

class GraphicMapNode(identifiers: TileIdentifiersCollection) extends RuleBookNode {

  private var graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map()

  def map: Map[TileIdentifier, GraphicDescriptor] = graphicMap

  def addGraphicDescription(tileIdentifier: TileIdentifier, graphicDescriptor: GraphicDescriptor): Unit = {
    if (graphicMap.contains(tileIdentifier)) {
      graphicMap += (tileIdentifier -> GraphicDescriptor.merge(graphicDescriptor, graphicMap(tileIdentifier)))
    } else {
      graphicMap += (tileIdentifier -> graphicDescriptor)
    }

  }

  override def check: Seq[String] = {
    graphicMap.keys
      .filter(_.number.isDefined)
      .map(_.number.get)
      .filter(!identifiers.containsNumber(_))
      .map("Tile " + _ + " define style but is not valid in this definition")
      .toSeq ++
      graphicMap.keys
        .filter(_.name.isDefined)
        .map(_.name.get)
        .filter(!identifiers.containsName(_))
        .map(_ + " name define style but is not assigned to any tile")
        .toSeq ++
      graphicMap.keys
        .filter(_.group.isDefined)
        .map(_.group.get)
        .filter(!identifiers.containsGroup(_))
        .map(_ + " group define style but is not assigned to any tile").toSeq
  }
}
