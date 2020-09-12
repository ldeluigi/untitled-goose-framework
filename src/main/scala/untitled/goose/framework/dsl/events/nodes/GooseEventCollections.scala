package untitled.goose.framework.dsl.events.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode

trait GooseEventCollections extends RuleBookNode {
  def gameEventCollection: EventCollection

  def playerEventCollection: EventCollection

  def tileEventCollection: EventCollection
}
