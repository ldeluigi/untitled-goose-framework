package untitled.goose.framework.dsl.nodes

/**
 * The base interface of all nodes.
 * The check method is used to return error messages.
 * If empty the node is considered correct and the check passed.
 */
trait RuleBookNode {
  def check: Seq[String]
}
