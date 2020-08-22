package view.logger

import java.text.SimpleDateFormat
import java.util.Calendar

import engine.events.root.GameEvent
import scalafx.scene.control.TextArea
import scalafx.scene.layout.Pane

/**
 * A custom pane used to log game events.
 */
trait EventLogger extends Pane {
  def logEvent(event: GameEvent): Unit
}

object EventLogger {

  private class EventLoggerImpl(height: Int) extends EventLogger {
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
  }

  /** A factory that renders a new EventLogger, given a certain height. */
  def apply(height: Int): EventLogger = new EventLoggerImpl(height)

}
