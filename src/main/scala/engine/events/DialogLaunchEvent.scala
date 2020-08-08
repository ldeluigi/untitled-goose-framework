package engine.events

import engine.events.root.AbstractGameEvent
import model.MatchState
import model.entities.DialogContent

case class DialogLaunchEvent(currentTurn: Long, createDialog: MatchState => DialogContent) extends AbstractGameEvent(currentTurn) {

}
