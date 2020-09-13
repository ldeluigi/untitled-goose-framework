package untitled.goose.framework.dsl.events.words

import untitled.goose.framework.dsl.events.nodes.{EventDefinitionCollection, EventNode}
import untitled.goose.framework.dsl.nodes.{RuleBook, RuleBookNode}
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events._

import scala.reflect.ClassTag

trait CustomEventInstance extends RuleBookNode {
  def properties: Map[Key[_], Any]

  def generateEvent(state: GameState): GameEvent

  def +[T: ClassTag](name: String, value: GameState => T): CustomEventInstance

  def name: String
}

object CustomEventInstance {

  private abstract class CustomEventInstanceImpl(override val name: String, definedEvents: EventDefinitionCollection) extends CustomEventInstance {

    var properties: Map[Key[_], GameState => Any] = Map()

    def +[T: ClassTag](name: String, value: GameState => T): CustomEventInstance = {
      properties += Key[T](name) -> value
      this
    }

    override def generateEvent(state: GameState): GameEvent = {
      val e: CustomGameEvent = initEvent(state)
      properties foreach { prop =>
        e.setProperty(prop._1.keyName, prop._2)
      }
      e
    }

    def initEvent(state: GameState): CustomGameEvent

    override def check: Seq[String] = if (definedEvents.isEventDefined(name)) {
      val e: EventNode = definedEvents.getEvent(name)
      properties.keySet.diff(e.properties).map(p =>
        "Trying to set property \"" + p + "\" for customGameEvent named \"" + name + "\" but it was never defined"
      ).toSeq ++
        e.properties.diff(properties.keySet).map(p =>
          "Property \"" + p + "\" for customGameEvent named \"" + name + "\" was not set but was defined"
        ).toSeq
    }
    else Seq("\"" + name + "\" is not defined in " + definedEvents.name)
  }

  def gameEvent(name: String, ruleBook: RuleBook): CustomEventInstance =
    new CustomEventInstanceImpl(name, ruleBook.eventDefinitions.gameEventCollection) {
      override def initEvent(state: GameState): CustomGameEvent =
        CustomGameEvent(state.currentTurn, state.currentCycle, name)
    }

  def playerEvent(name: String, player: GameState => Player, ruleBook: RuleBook): CustomEventInstance =
    new CustomEventInstanceImpl(name, ruleBook.eventDefinitions.playerEventCollection) {
      override def initEvent(state: GameState): CustomGameEvent =
        CustomPlayerEvent(state.currentTurn, state.currentCycle, name, player(state))
    }

  def tileEvent(name: String, tile: GameState => Tile, ruleBook: RuleBook): CustomEventInstance =
    new CustomEventInstanceImpl(name, ruleBook.eventDefinitions.tileEventCollection) {
      override def initEvent(state: GameState): CustomGameEvent =
        CustomTileEvent(state.currentTurn, state.currentCycle, name, tile(state))
    }
}