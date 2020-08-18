package model.entities

import engine.events.GameEvent

trait DialogContent {

  def title: String

  def text: String

  def options: Map[String, GameEvent]
}

object DialogContent {
  def apply(dialogTitle: String, dialogText: String, answers: (String, GameEvent)*): DialogContent =
    new DialogContent {
      override def title: String = dialogTitle

      override def text: String = dialogText

      override def options: Map[String, GameEvent] = answers.toMap
    }
}


