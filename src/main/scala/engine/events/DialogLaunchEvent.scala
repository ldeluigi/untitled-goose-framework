package engine.events

import engine.events.root.ConsumableGameEvent
import model.MutableMatchState
import model.entities.DialogContent

case class DialogLaunchEvent(currentTurn: Int, createDialog: MutableMatchState => DialogContent) extends ConsumableGameEvent(currentTurn) {

}
