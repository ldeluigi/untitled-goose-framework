package dsl

class RuleBook extends RuleBookNode {

  private val gameName: SingleValueNode[String] = new SingleValueNode()

  def setGameName(name: String): Unit = {
    gameName value = name
  }

  override def toString: String =
    gameName value

  override def check: Boolean = gameName.check
}
