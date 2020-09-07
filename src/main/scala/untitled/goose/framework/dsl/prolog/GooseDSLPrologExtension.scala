package untitled.goose.framework.dsl.prolog

import it.unibo.tuprolog.solve._
import untitled.goose.framework.model.entities.runtime.GameState


trait GooseDSLPrologExtension {
  implicit def modelCondition(s: String): GameState => Boolean = {
    val solver = SolverFactory.DefaultImpls.solverWithDefaultBuiltins(SolverFactory)
    ???
  }
}
