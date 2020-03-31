import java.io.{ByteArrayOutputStream, PrintStream}

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.{BeforeAndAfter, GivenWhenThen}

class CoffeeMachineSpec extends AnyFeatureSpec with GivenWhenThen with BeforeAndAfter {

  private val money = 1.0

  private val errorContent = new ByteArrayOutputStream
  private val originalErrorContent = System.err

  before {
    System.setErr(new PrintStream(errorContent))
  }

  after {
    System.setErr(originalErrorContent)
  }

  Feature("First step") {
    Scenario("Drink maker makes 1 tea with 1 sugar and a stick") {
      val input = "T:1:0"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "Tea with 1 sugar and 1 stick")
    }

    Scenario("Drink maker makes 1 chocolate with no sugar - and therefore no stick") {
      val input = "H::"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "HotChocolate with 0 sugar and no stick")
    }

    Scenario("Drink maker makes 1 coffee with 2 sugars and a stick") {
      val input = "C:2:0"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "Coffee with 2 sugar and 1 stick")
    }

    Scenario("Drink maker forwards any message received onto the coffee machine interface for the customer to see") {
      val input = "M:message-content"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "message-content")
    }
  }

  Feature("Second step") {
    Scenario("Drink maker makes no drink cause not enough money is given") {
      val input = "C:1:0"
      val result = CoffeeMachine.takeCommand(input, 0)

      assert(result === "You need to put 0.6 more to get a Coffee")
    }
  }

  Feature("Third step") {
    Scenario("Drink maker will make one orange juice") {
      val input = "O::"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "OrangeJuice with 0 sugar and no stick")
    }

    Scenario("Drink maker will make an extra hot coffee with no sugar") {
      val input = "Ch::"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "ExtraHot Coffee with 0 sugar and no stick")
    }

    Scenario("Drink maker will make an extra hot chocolate with one sugar and a stick") {
      val input = "Hh:1:0"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "ExtraHot HotChocolate with 1 sugar and 1 stick")
    }

    Scenario("The drink maker will make an extra hot tea with two sugar and a stick") {
      val input = "Th:2:0"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "ExtraHot Tea with 2 sugar and 1 stick")
    }
  }

  Feature("fourth step") {
    Scenario("Checking all drinks sold") {
      val input = "R:"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "Tea sold: 2\nCoffee sold: 2\nHotChocolate sold: 2\nOrangeJuice sold: 1")
    }
  }

  Feature("fifth step") {
    Scenario("No more drink available and a notification is sent") {
      val input = "C:2:0"
      val result = CoffeeMachine.takeCommand(input, money)

      assert(result === "No more Coffee is available, an email has been sent to refill the machine")

      val errorLines = errorContent.toString.split("\n")
      assert(errorLines.length === 1)
      assert(errorLines.head === "Drink Coffee is missing, replace ASAP")
    }
  }
}
