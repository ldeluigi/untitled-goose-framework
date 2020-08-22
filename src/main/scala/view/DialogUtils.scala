package view

import engine.events.root.{GameEvent, NoOpEvent}
import model.entities.DialogContent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

import scala.concurrent.Promise

/** Custom dialog properties modeling.  */
object DialogUtils {

  /**
   * @param content the content of the dialog to be diplayed.
   * @param promise the promise used to check the action the user has choosen.
   */
  def launchDialog(content: DialogContent, promise: Promise[GameEvent]): Unit = {
    val alert = new Alert(AlertType.Confirmation) {
      title = content.title
      headerText = content.text

      buttonTypes = if (content.options.isEmpty) {
        Seq(ButtonType.OK)
      } else {
        content.options.map(opt => new ButtonType(opt._1))
      }
    }

    val result = alert.showAndWait()
    result match {
      case Some(ButtonType.OK) => promise.success(NoOpEvent)
      case Some(value) => promise.success(content.options(value.text))
      case None => promise.failure(new IllegalStateException("-x-Dialog cannot be closed without answering"))
    }
  }
}
