package engine.events

import engine.events.root.GameEvent
import model.Player

class DiceRollEvent(groupList: List[String], player: Player) extends GameEvent {

  override def name: String = "DiceRoll"

  override def group: List[String] = groupList

  // TODO sides and dice result generation modeling
}
