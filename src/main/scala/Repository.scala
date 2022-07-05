case class Person(id: Long, name: String, email: String)

case class Account(id: Long, ownerId: Long)

trait AccountRepository {
  def findById(id: Long): Account
  def saveAccount(account: Account): Unit
}

trait PersonRepository {
  def findById(id: Long): Person
}
