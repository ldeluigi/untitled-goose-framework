package controller.scalafx

import controller.engine.vertx.GooseEngine
import model.actions.Action
import model.entities.DialogContent
import model.entities.runtime.{Game, GameState}
import model.events.GameEvent
import view.scalafx.GameScene

import scala.concurrent.{Future, Promise}

/**
 * Implementation for a controller for the Goose Engine Frameworks that uses a ScalaFx View.
 * @param game The game that encapsulates the mutable state and the rules.
 */
case class ApplicationController(game: Game) extends ScalaFxController {

  private val engine: GooseEngine = GooseEngine(game, this)
  private var gameScene: Option[GameScene] = None

  override def setScene(gameScene: GameScene): Unit = {
    this.gameScene = Some(gameScene)
  }

  override def resolveAction(action: Action): Unit =
    engine.eventSink.accept(action.trigger(engine.game.currentState))

  override def update(state: GameState): Unit = {
    val clonedState: GameState = state.clone()
    gameScene.get.updateScene(clonedState, engine.game.availableActions)
  }

  override def close(): Unit = {
    stopGame()
    gameScene.get.close()
  }

  override def logAsyncEvent(event: GameEvent): Unit = gameScene.get.logEvent(event)


  override def showDialog(content: DialogContent): Future[GameEvent] = {
    val promise: Promise[GameEvent] = Promise()
    gameScene.get.showDialog(content, promise)
    promise.future
  }

  override def stopGame(): Unit = engine.stop()
}
