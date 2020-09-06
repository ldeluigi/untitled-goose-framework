package untitled.goose.framework.dsl.nodes

class SingleValueNode[T](name: String) extends RuleBookNode {
  private var v: Option[T] = None

  def isDefined: Boolean = v.isDefined

  def value_=(value: T): Unit = v = Some(value)

  def value: T = v.get

  override def check: Seq[String] = if (v.isDefined) Seq() else Seq("Missing: " + name)

}
