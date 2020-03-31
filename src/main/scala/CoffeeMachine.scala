

object CoffeeMachine {

  def main(args: Array[String]): Unit = {
    println(takeCommand(args.head))
  }

  def takeCommand(input: String): String = {
    Command.parse(input) match {
      case None => "error"
      case Some(command: MessageCommand) => command.message
      case Some(command: DrinkCommand) => Drink(command.drinkType, command.sugar).toString
    }
  }
}
