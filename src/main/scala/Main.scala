import Service.{AbstractModule, EmailService}

object Main{
  def main(args: Array[String]): Unit = {

    trait LiveEmailService extends EmailService{
      def sendEmail(address: String, text: String): Unit = println(s"sent message: $text to $address")
    }

    object LiveModule extends AbstractModule with LiveEmailService {
      override def personRepository: PersonRepository = (id: Long) => Person(id, "khanhdb", "khanhdb@gmail.com")
      override def accountRepository: AccountRepository = new AccountRepository {
        override def findById(id: Long): Account = Account(id, 22)
        override def saveAccount(account: Account): Unit = {
          println(s"saved account $account")
        }
      }
    }
    val name = Service.findPersonNameByAccountId(2L).run(LiveModule)
    println(name)

    Service.openAccount(2, 2).run(LiveModule)
  }
}
