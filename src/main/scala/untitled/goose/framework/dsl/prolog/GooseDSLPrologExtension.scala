package untitled.goose.framework.dsl.prolog

import alice.tuprolog.lib.OOLibrary
import alice.tuprolog.{Prolog, Struct}
import untitled.goose.framework.model.entities.runtime.GameState


trait GooseDSLPrologExtension {
  implicit def modelCondition(s: String): GameState => Boolean =
    state => {
      val engine: Prolog = new Prolog()
      val lib: OOLibrary = engine.getLibrary("alice.tuprolog.lib.OOLibrary").asInstanceOf[OOLibrary]
      val gameStateId: Struct = Struct.atom("gamestate")
      lib.register(gameStateId, state)

      lib.unregister(gameStateId)
    }

}
