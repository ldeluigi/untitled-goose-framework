package untitled.goose.framework.view.scalafx

import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.special.NoOpEvent
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.control.Alert.AlertType

import scala.concurrent.Promise

/** Custom dialog properties modeling.  */
object DialogUtils {

  /**
   * @param content the content of the dialog to be displayed.
   * @param promise the promise used to check the action the user has chosen.
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
