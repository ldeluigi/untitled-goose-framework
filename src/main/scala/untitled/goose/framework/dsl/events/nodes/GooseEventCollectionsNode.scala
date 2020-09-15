package untitled.goose.framework.dsl.events.nodes

case class GooseEventCollectionsNode() extends GooseEventCollections {
  private val gameEvent: EventDefinitionsNode = EventDefinitionsNode("Game Events")
  private val playerEvent: EventDefinitionsNode = EventDefinitionsNode("Player Events")
  private val tileEvent: EventDefinitionsNode = EventDefinitionsNode("Tile Events")

  override def check: Seq[String] =
    gameEvent.check ++
      playerEvent.check ++
      tileEvent.check

  override def gameEventCollection: EventDefinitionCollection = gameEvent

  override def playerEventCollection: EventDefinitionCollection = playerEvent

  override def tileEventCollection: EventDefinitionCollection = tileEvent
}
