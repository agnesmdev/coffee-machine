

object CoffeeMachine {

  private val emailNotifier = new EmailNotifierMocker()
  private val beverageQuantityChecker = new BeverageQuantityCheckerMocker()

  private var drinks: Seq[Drink] = Nil

  def main(args: Array[String]): Unit = {
    val money = if (args.length > 1) args(1).toDouble else 0
    println(takeCommand(args.head, money))
  }

  def takeCommand(input: String, money: Double): String = {
    Command.parse(input) match {
      case None => "error"
      case Some(command: MessageCommand) => command.message
      case Some(ReportCommand) => report
      case Some(command: DrinkCommand) =>
        val drink = Drink(command.drinkType, command.sugar, command.hot)
        val missingMoney = command.drinkType.price - money

        if (missingMoney > 0) {
          s"You need to put $missingMoney more to get a ${drink.drinkType}"
        } else if (beverageQuantityChecker.isEmpty(drink.drinkType)) {
          emailNotifier.notifyMissingDrink(drink.drinkType)
          s"No more ${drink.drinkType} is available, an email has been sent to refill the machine"
        } else {
          drinks ++= Seq(drink)
          drink.toString
        }
    }
  }

  private def report: String = {
    drinks.groupBy(_.drinkType).map { case (drinkType, d) =>
      s"$drinkType sold: ${d.length}"
    }.mkString("\n")
  }
}
