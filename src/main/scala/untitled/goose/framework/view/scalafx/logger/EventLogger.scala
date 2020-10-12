package untitled.goose.framework.view.scalafx.logger

import scalafx.scene.control.TextArea
import scalafx.scene.layout.Pane
import untitled.goose.framework.model.entities.runtime.{GameState, Player, Tile}
import untitled.goose.framework.model.events.GameEvent

/**
 * A custom pane used to log runtime events.
 */
trait EventLogger extends Pane {

  /** Prints events, that need to be logged, on a text area contained in itself.
   *
   * @param event the event to be logged
   */
  def logEvent(event: GameEvent): Unit

  /** Computes the logger's differences with the previous match's state logger.
   *
   * @param state the newly computed state.
   */
  def logHistoryDiff(state: GameState): Unit
}

object EventLogger {

  private class EventLoggerImpl(gameState: GameState) extends EventLogger {

    private var eventNumber: Map[String, Int] = Map()
    private var previousState: GameState = gameState
    private val logText: TextArea = new TextArea {
      wrapText = true
      editable = false
    }

    this.children.add(logText)
    logText.prefWidth <== this.width
    logText.prefHeight <== this.height - 10

    private def logEvent(event: GameEvent, kind: String): Unit = {
      val i = eventNumber.getOrElse(kind, 0)
      logText.appendText("\n" + (if (kind.length > 0) kind + " event" else "Event") + " [" + i + "]: " + event.toString)
      logText.scrollTop = Double.MaxValue
      eventNumber += (kind -> (i + 1))
    }

    override def logEvent(event: GameEvent): Unit = logEvent(event, "")

    override def logHistoryDiff(state: GameState): Unit = {
      // TODO FIX: don't use diff!
      (state.consumableBuffer
        .diff(previousState.consumableBuffer).map((_, "Consumable")) ++
        state.gameHistory
          .diff(previousState.gameHistory).map((_, "Game")) ++
        state.players.values.toSet
          .flatMap((p: Player) => p.history)
          .diff(previousState.players.values.toSet
            .flatMap((p: Player) => p.history)).map((_, "Player")) ++
        state.gameBoard.tiles.values.toSet
          .flatMap((t: Tile) => t.history)
          .diff(previousState.gameBoard.tiles.values.toSet
            .flatMap((t: Tile) => t.history)).map((_, "Tile")))
        .foreach(d => logEvent(d._1, d._2))
      previousState = state
    }
  }

  /** A factory that renders a new EventLogger, given a certain height. */
  def apply(gameState: GameState): EventLogger = new EventLoggerImpl(gameState)

}
