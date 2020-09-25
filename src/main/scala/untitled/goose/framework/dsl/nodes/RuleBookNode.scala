package untitled.goose.framework.dsl.nodes

/**
 * The base interface of all nodes.
 * The check method is used to return error messages.
 * If empty the node is considered correct and the check passed.
 */
trait RuleBookNode {

  /**
   * Checks the model. Returns a sequence of error messages.
   *
   * @return a sequence of error messages. If empty, check passed.
   */
  def check: Seq[String]
}
