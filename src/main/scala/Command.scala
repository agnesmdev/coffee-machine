
sealed trait Command {
  def commandType: CommandType

  def message: String
}

sealed trait CommandType {
  def value: String
}

sealed trait DrinkType extends CommandType {
  def price: Double
}

case class MessageCommand(message: String) extends Command {
  override val commandType: CommandType = Message
}

case object ReportCommand extends Command {
  override val commandType: CommandType = Report

  override val message: String = ""
}

case class DrinkCommand(drinkType: DrinkType, hot: Boolean, sugar: Int) extends Command {
  override val commandType: CommandType = drinkType

  override val message: String = if (sugar == 0) "::" else s"$sugar:0"
}

case object Message extends CommandType {
  override val value: String = "M"
}

case object Report extends CommandType {
  override val value: String = "R"
}

case object Tea extends DrinkType {
  override val value: String = "T"

  override val price: Double = 0.4
}

case object HotChocolate extends DrinkType {
  override val value: String = "H"

  override val price: Double = 0.5
}

case object Coffee extends DrinkType {
  override val value: String = "C"

  override val price: Double = 0.6
}

case object OrangeJuice extends DrinkType {
  override val value: String = "O"

  override val price: Double = 0.6
}

object DrinkType {
  def parse(value: String): Option[DrinkType] = value match {
    case Tea.value => Some(Tea)
    case HotChocolate.value => Some(HotChocolate)
    case Coffee.value => Some(Coffee)
    case OrangeJuice.value => Some(OrangeJuice)
    case _ => None
  }
}

object DrinkCommand {
  def apply(t: DrinkType, h: String, s: String): DrinkCommand = {
    DrinkCommand(t, h.nonEmpty, if (s.isEmpty) 0 else s.toInt)
  }
}

object Command {
  private val drinkRegex = "(\\w)([h]?):(\\d*):(\\d*)".r
  private val messageRegex = "(\\w):(.*)".r

  def parse(command: String): Option[Command] = {
    command match {
      case drinkRegex(t, h, s, _) => DrinkType.parse(t).map(DrinkCommand(_, h, s))
      case messageRegex(t, m) if t == Message.value => Some(MessageCommand(m))
      case messageRegex(t, _) if t == Report.value => Some(ReportCommand)
      case _ => None
    }
  }
}
