package model.rules.ruleset

/** Defines rules priority */
object RulePriorities {

  trait GooseFrameworkPriorities {
    val loseTurnPriority: Int
  }

  implicit object DefaultPriorities extends GooseFrameworkPriorities {
    override val loseTurnPriority: Int = 10
  }
}
