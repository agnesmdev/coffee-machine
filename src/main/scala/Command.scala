
sealed trait Command {
  def commandType: CommandType

  def message: String
}

case class MessageCommand(message: String) extends Command {
  override val commandType: CommandType = Message
}

case class DrinkCommand(drinkType: DrinkType, sugar: Int) extends Command {
  override val commandType: CommandType = drinkType

  override val message: String = if (sugar == 0) "::" else s"$sugar:0"
}

sealed trait CommandType {
  def value: String
}

case object Message extends CommandType {
  override val value: String = "M"
}

sealed trait DrinkType extends CommandType

case object Tea extends DrinkType {
  override val value: String = "T"
}

case object HotChocolate extends DrinkType {
  override val value: String = "H"
}

case object Coffee extends DrinkType {
  override val value: String = "C"
}

object DrinkType {
  def parse(value: String): Option[DrinkType] = value match {
    case Tea.value => Some(Tea)
    case HotChocolate.value => Some(HotChocolate)
    case Coffee.value => Some(Coffee)
    case _ => None
  }
}

object DrinkCommand {
  def apply(t: DrinkType, s: String): DrinkCommand = {
    DrinkCommand(t, if (s.isEmpty) 0 else s.toInt)
  }
}

object Command {
  private val drinkRegex = "(.*):(\\d*):(\\d*)".r
  private val messageRegex = "(.*):(.*)".r

  def parse(command: String): Option[Command] = {
    command match {
      case drinkRegex(t, s, _) => DrinkType.parse(t).map(DrinkCommand(_, s))
      case messageRegex(t, m) if t == Message.value => Some(MessageCommand(m))
      case _ => None
    }
  }
}
