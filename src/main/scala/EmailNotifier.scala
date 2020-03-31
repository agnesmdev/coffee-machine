

trait EmailNotifier {

  def notifyMissingDrink(drinkType: DrinkType): Unit
}

class EmailNotifierMocker extends EmailNotifier {

  override def notifyMissingDrink(drinkType: DrinkType): Unit = {
    System.err.println(s"Drink $drinkType is missing, replace ASAP")
  }
}