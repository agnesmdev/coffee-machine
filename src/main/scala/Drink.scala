

case class Drink(drinkType: DrinkType, sugar: Int, extraHot: Boolean) {
  lazy val stick: String = if (sugar > 0)  "1" else "no"

  lazy val hot: String = if (extraHot) "ExtraHot " else ""

  override def toString: String = s"$hot$drinkType with $sugar sugar and $stick stick"
}

