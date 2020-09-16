package untitled.goose.framework.dsl.prolog

import untitled.goose.framework.model.entities.runtime.GameState


trait GooseDSLPrologExtension {
  implicit def modelCondition(s: String): GameState => Boolean = {
    //val solver = SolverFactory.DefaultImpls.solverWithDefaultBuiltins()
    ???
  }
}
