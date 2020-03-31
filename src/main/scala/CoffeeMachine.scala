

object CoffeeMachine {

  def main(args: Array[String]): Unit = {
    val money = if (args.length > 1) args(1).toDouble else 0
    println(takeCommand(args.head, money))
  }

  def takeCommand(input: String, money: Double): String = {
    Command.parse(input) match {
      case None => "error"
      case Some(command: MessageCommand) => command.message
      case Some(command: DrinkCommand) =>
        val drink = Drink(command.drinkType, command.sugar, command.hot)
        val missingMoney = command.drinkType.price - money

        if (missingMoney > 0) {
          s"You need to put $missingMoney more to get a ${drink.drinkType}"
        } else {
          drink.toString
        }
    }
  }
}
