package dsl.nodes

import model.TileIdentifier
import view.board.GraphicDescriptor

class GraphicMapNode() extends RuleBookNode {

  private var graphicMap: Map[TileIdentifier, GraphicDescriptor] = Map()

  def addGraphicDescription(tileIdentifier: TileIdentifier, graphicDescriptor: GraphicDescriptor): Unit =
    graphicMap = graphicMap + (tileIdentifier -> graphicDescriptor)

  override def check: Boolean = true
}
