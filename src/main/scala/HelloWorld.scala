import scala.collection.mutable.{ListBuffer, Map}
import concurrent.ExecutionContext.Implicits.global
import concurrent.{Await, Future}
import scala.util.{Failure, Success}
import concurrent.duration._

object HelloWorld extends App {
  /*val a = List(1, 2)
  var b = ListBuffer(3, 4)
  var c = ListBuffer(3, 4, 5)

  val s = Map(a -> 1, b -> 2, c -> 3)
  println(s)

  b.append(5)
  c.append(6)

  println(b)
  println(s)
  println(s(ListBuffer(3, 4, 5)))*/

  val commits = Future sequence Seq(
    Future(GetGitHubCommits.get("jaeminkim87", "flask_docker_study")),
    Future(GetGitHubCommits.get("jaeminkim87", "uber_clone"))
  )

  Await.result(commits, Duration(5, SECONDS))

  commits.onComplete {
    case Success(value) => value.foreach(commit => commit.foreach(list => {
      println("========================")
      println(s"title : ${list._2.trim}")
      println(s"author : ${list._1.trim.replace(" ","").replace("\n", " ")}")
      println(s"updated : ${list._3.trim}")
    }))
    case Failure(exception) => println(exception)
  }
}
