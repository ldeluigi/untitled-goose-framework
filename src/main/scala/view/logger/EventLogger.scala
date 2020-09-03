package view.logger

import java.text.SimpleDateFormat
import java.util.Calendar

import model.entities.runtime.GameState
import model.events.GameEvent
import scalafx.scene.control.TextArea
import scalafx.scene.layout.Pane

/**
 * A custom pane used to log runtime events.
 */
trait EventLogger extends Pane {
  def logEvent(event: GameEvent): Unit

  def logHistoryDiff(state: GameState): Unit
}

object EventLogger {

  private class EventLoggerImpl(gameState: GameState, height: Int) extends EventLogger {

    var previousState: GameState = gameState
    val logText: TextArea = new TextArea {
      wrapText = true
      editable = false
    }

    this.children.add(logText)
    logText.prefWidth <== this.width
    logText.prefHeight = height

    /** Prints events, that need to be logged, on a text area contained in itself.
     *
     * @param event the event to be logged
     */
    def logEvent(event: GameEvent): Unit = {
      val now = Calendar.getInstance().getTime
      val minuteFormat = new SimpleDateFormat("[HH:mm:ss]")
      val timestamp = minuteFormat.format(now)
      logText.appendText("\n" + timestamp + " - EVENT " + event.toString)
      logText.scrollTop = Double.MaxValue
    }

    def logHistoryDiff(state: GameState): Unit = {
      (state.consumableBuffer.diff(previousState.consumableBuffer) ++
        state.players.flatMap(_.history).diff(previousState.players.flatMap(_.history)) ++
        state.gameBoard.tiles.flatMap(_.history).diff(previousState.gameBoard.tiles.flatMap(_.history)))
        .foreach(logEvent)
      previousState = state
    }
  }

  /** A factory that renders a new EventLogger, given a certain height. */
  def apply(gameState: GameState, height: Int): EventLogger = new EventLoggerImpl(gameState, height)

}
