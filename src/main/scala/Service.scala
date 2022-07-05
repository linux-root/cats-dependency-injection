import cats.data.Reader

object Service {

  trait AbstractModule extends AccountModule with PersonModule with EmailService

  trait AccountModule {
    def accountRepository: AccountRepository
  }

  trait PersonModule {
    def personRepository: PersonRepository
  }

  trait EmailService {
    def sendEmail(address: String, text: String): Unit
  }


  def findPersonNameByAccountId(id: Long): Reader[AccountModule with PersonModule, String] = for {
    accountModule <- Reader(identity[AccountModule])
    personModule <- Reader(identity[PersonModule])
    acc = accountModule.accountRepository.findById(id)
    person = personModule.personRepository.findById(acc.ownerId)
  } yield person.name


  def openAccount(accountId: Long, ownerId: Long): Reader[AbstractModule, Unit] = {
    for {
      accountModule <- Reader(identity[AccountModule])
      personModule <- Reader(identity[PersonModule])
      emailService <- Reader(identity[EmailService])

      _ = accountModule.accountRepository.saveAccount(Account(accountId, ownerId))
      person = personModule.personRepository.findById(ownerId)
      _ = emailService.sendEmail(person.email, "Your account is created successfully")
    } yield ()
  }
}
