package untitled.goose.framework.dsl.events.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode

trait GooseEventCollections extends RuleBookNode {
  def gameEventCollection: EventDefinitionCollection

  def playerEventCollection: EventDefinitionCollection

  def tileEventCollection: EventDefinitionCollection
}
