package dsl

trait RuleBookNode {
  def check: Boolean
}

class SingleValueNode[T] extends RuleBookNode {
  private var v: Option[T] = None

  def value_=(value: T): Unit = v = Some(value)

  def value: T = v.get

  override def check: Boolean = v.isDefined

}
