package view.logger

import java.text.SimpleDateFormat
import java.util.Calendar

import engine.events.root.GameEvent
import scalafx.scene.control.TextArea
import scalafx.scene.layout.Pane

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

    def logEvent(event: GameEvent): Unit = {
      val now = Calendar.getInstance().getTime
      val minuteFormat = new SimpleDateFormat("[HH:mm:ss]")
      val timestamp = minuteFormat.format(now)
      logText.appendText("\n" + timestamp + " - EVENT " + event.toString)
      logText.scrollTop = Double.MaxValue
    }
  }


  def apply(height: Int): EventLogger = new EventLoggerImpl(height)

}
