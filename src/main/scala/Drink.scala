

case class Drink(drinkType: DrinkType, sugar: Int) {
  lazy val stick: String = if (sugar > 0)  "1" else "no"

  override def toString: String = s"$drinkType with $sugar sugar and $stick stick"
}

