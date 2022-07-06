import cats.data.Reader
import cats.implicits.catsSyntaxApplicativeId
import cats.syntax.ApplicativeSyntax

case class DB(usernames: Map[Int, String], passwords: Map[String, String])

type DBReader[T] = Reader[DB, T]

object DBOps {
  def findUsername(uid: Int): DBReader[Option[String]] = for {
      db <- Reader(identity[DB])
      username = db.usernames.get(uid)
    } yield username

  def checkPassword(username: String, password: String): DBReader[Boolean] = for {
    db <- Reader(identity[DB])
    result = db.passwords.get(username).contains(password)
  } yield result

  def checkLogin(uid: Int, password: String): DBReader[Boolean] = for {
    username <- findUsername(uid)
    result <- username match {
      case Some(n) =>
        checkPassword(n, password)
      case None =>
//        Reader[DB, Boolean](_ => false)
        false.pure[DBReader] // better syntax
    }
  } yield result
}

val mockDB = DB(Map(1 -> "watson", 2 -> "kaycee"), Map("watson" -> "secret", "kaycee" -> "s3cr3t"))

DBOps.checkLogin(1, "secret").run(mockDB)