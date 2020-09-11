package untitled.goose.framework.view.scalafx.logger

import scalafx.scene.control.TextArea
import scalafx.scene.layout.Pane
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

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
      logText.appendText("\nEVENT: " + event.toString)
      logText.scrollTop = Double.MaxValue
    }

    def logHistoryDiff(state: GameState): Unit = {
      (state.consumableBuffer.diff(previousState.consumableBuffer) ++
        state.gameHistory.diff(previousState.gameHistory) ++
        state.players.flatMap(_.history).diff(previousState.players.flatMap(_.history)) ++
        state.gameBoard.tiles.flatMap(_.history).diff(previousState.gameBoard.tiles.flatMap(_.history)))
        .foreach(logEvent)
      previousState = state
    }
  }

  /** A factory that renders a new EventLogger, given a certain height. */
  def apply(gameState: GameState, height: Int): EventLogger = new EventLoggerImpl(gameState, height)

}
