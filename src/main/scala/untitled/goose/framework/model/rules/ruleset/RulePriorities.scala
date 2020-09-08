package untitled.goose.framework.model.rules.ruleset

object RulePriorities {

  /** Priorities of built-in action availabilities. */
  trait GooseFrameworkPriorities {
    val loseTurnPriority: Int
    // TODO aren't we missing something here?
  }

  /** Default priorities values. */
  implicit object DefaultPriorities extends GooseFrameworkPriorities {
    override val loseTurnPriority: Int = 10
  }

}
