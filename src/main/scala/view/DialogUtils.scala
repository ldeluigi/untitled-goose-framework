package view

import engine.events.root.GameEvent
import model.entities.DialogContent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

import scala.concurrent.Promise

object DialogUtils {

  def launchDialog(content: DialogContent, promise: Promise[GameEvent]): Unit = {
    val alert = new Alert(AlertType.Confirmation) {
      title = content.title
      headerText = content.text
      //TODO Check if the options are empty
      buttonTypes = content.options.map(opt => new ButtonType(opt._1))
    }

    val result = alert.showAndWait()
    result match {
      case Some(value) => promise.success(content.options(value.text))
      case None => promise.failure(new IllegalStateException("-x-Dialog cannot be closed without answering"))
    }
  }
}
