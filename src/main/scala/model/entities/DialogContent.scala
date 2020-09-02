package model.entities

import model.events.GameEvent

trait DialogContent {

  /**
   * @return the dialog's title
   */
  def title: String

  /**
   * @return the dialog's main info text
   */
  def text: String

  /**
   * @return a map linking the dialog's user answers to its related event.
   */
  def options: Map[String, GameEvent]

  override def equals(obj: Any): Boolean = obj match {
    case obj: DialogContent => obj.title == title && obj.text == text && obj.options == options
    case _ => false
  }

}

object DialogContent {

  /** A factory creating a new user dialog based on a title, a text, and possible user answers. */
  def apply(dialogTitle: String, dialogText: String, answers: (String, GameEvent)*): DialogContent =
    new DialogContent {
      override def title: String = dialogTitle

      override def text: String = dialogText

      override def options: Map[String, GameEvent] = answers.toMap
    }

}


