package model.rules.ruleset

object RulePriorities {

  trait GooseFrameworkPriorities {
    val loseTurnPriority: Int
  }

  implicit object DefaultPriorities extends GooseFrameworkPriorities {
    override val loseTurnPriority: Int = 10
  }
}
