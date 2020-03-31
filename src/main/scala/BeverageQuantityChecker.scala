

trait BeverageQuantityChecker {

  def isEmpty(drinkType: DrinkType): Boolean
}

class BeverageQuantityCheckerMocker extends BeverageQuantityChecker {

  private var quantities: Map[DrinkType, Int] = Map(Tea -> 2, HotChocolate -> 2, Coffee -> 2, OrangeJuice -> 2)

  override def isEmpty(drinkType: DrinkType): Boolean = {
    quantities(drinkType) match {
      case 0 => true
      case n =>
        quantities += drinkType -> (n - 1)
        false
    }
  }
}