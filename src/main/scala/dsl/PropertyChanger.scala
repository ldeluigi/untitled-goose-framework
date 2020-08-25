package dsl

trait PropertyChanger[S <: PropertyBuilder] {
  def applyChange(builder: S): Unit
}
