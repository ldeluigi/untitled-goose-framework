package untitled.goose.framework.model.entities.runtime

/** Represent a runtime entity that has a definition. */
trait Defined[D] {

  /** Returns the definition of this runtime entity. */
  def definition: D
}
