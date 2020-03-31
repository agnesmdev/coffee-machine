import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec

class CoffeeMachineSpec extends AnyFeatureSpec with GivenWhenThen {

  Feature("Make drink") {
    Scenario("Drink maker makes 1 tea with 1 sugar and a stick") {
      val input = "T:1:0"
      val result = CoffeeMachine.takeCommand(input)

      val drink = Drink(Tea, 1)
      assert(result === drink.toString)
    }

    Scenario("Drink maker makes 1 chocolate with no sugar - and therefore no stick") {
      val input = "H::"
      val result = CoffeeMachine.takeCommand(input)

      val drink = Drink(HotChocolate, 0)
      assert(result === drink.toString)
    }

    Scenario("Drink maker makes 1 coffee with 2 sugars and a stick") {
      val input = "C:2:0"
      val result = CoffeeMachine.takeCommand(input)

      val drink = Drink(Coffee, 2)
      assert(result === drink.toString)
    }

    Scenario("Drink maker forwards any message received onto the coffee machine interface for the customer to see") {
      val input = "M:message-content"
      val result = CoffeeMachine.takeCommand(input)

      assert(result === "message-content")
    }
  }
}
