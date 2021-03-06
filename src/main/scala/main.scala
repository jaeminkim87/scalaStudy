import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.collection.mutable
import scala.concurrent.duration.{Duration, SECONDS}
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object main extends App {
  @annotation.tailrec
  def fibonacci(l: mutable.ListBuffer[Int], count: Int): mutable.Buffer[Int] = {
    if (count == 0) {
      l
    } else {
      var c = count - 1
      if (l.isEmpty) {
        l ++= mutable.ListBuffer(1, 1)
        c = c - 2
      }
      l += l(l.size - 2) + l.last
      fibonacci(l, c)
    }
  }

  def fibonacci1(count: Int) = {
    val b = List(1, 1).toBuffer
    while (b.size < count) b += b.takeRight(2).sum
    b.toList
  }

  // 1-a
  val l = mutable.ListBuffer.empty[Int]
  val a = fibonacci(l, 10)
  println(a.toList)

  val r = fibonacci1(10)
  println(r)

  // 1-b
  val l1 = mutable.ListBuffer(1, 1, 2, 3, 5)
  val b = fibonacci(l1, 10)
  println(b.toList)

  // 1-c
  def fibonacciStream(prev: BigInt, next:BigInt):LazyList[BigInt] = LazyList.cons(prev, fibonacciStream(next, prev + next))
  val s = fibonacciStream(1,1)
  val list = s.take(100).toList
  //println(list)

  val iteratorList = list grouped 10 map (_.mkString(","))
  println(iteratorList foreach println)

  // 1-d
  def fibNext(element: Int):Option[BigInt] = {
    val s = fibonacciStream(1,1)
    val r = s.takeWhile(_ <= element).toList
    if(r.last == element) Some(r.takeRight(2).sum)
    else None
  }
  println(fibNext(8))

  // 2
  def returnFileName(path: String): List[String] = {
    val list = new java.io.File(path).listFiles.toList.map(_.toString.replace(path, ""))
    list.filterNot(_.contains("."))
  }

  val getFiles = returnFileName("D:\\projects\\allaboutscala\\")
  println(getFiles)

  // 3
  val groups = getFiles.groupBy(_.head.toLower)
  for ((char, list) <- groups) println(s"${char} has ${list.size}")

  // 4
  def multiple(a: String, b: String) = {
    (a.toDoubleOption, b.toDoubleOption) match {
      case (Some(a), Some(b)) => a * b
      case _ => None
    }
  }

  println(multiple("1.0", "2.0"))

  // 5
  def checkProperty(property: String):Option[String] = {
    util.Try(System.getProperty(property)) match {
      case util.Success(x) => Option(x)
      case util.Failure(ex) => None
    }
  }
  println(checkProperty(""))

  //6
  val commits = Future sequence Seq(
    Future(GetGitHubCommits.get("jaeminkim87", "flask_docker_study")),
    Future(GetGitHubCommits.get("jaeminkim87", "uber_clone"))
  )

  Await.result(commits, Duration(5, SECONDS))

  /*commits.onComplete {
    case Success(value) => value.foreach(commit => commit.foreach(list => {
      println("========================")
      println(s"title : ${list._2.trim}")
      println(s"author : ${list._1.trim.replace(" ","").replace("\n", " ")}")
      println(s"updated : ${list._3.trim}")
    }))
    case Failure(exception) => println(exception)
  }*/

  commits.onComplete {
    case Success(value) => value.flatten.sortWith(_._2 < _._2).foreach(list => {
      println("========================")
      println(s"title : ${list._2.trim}")
      println(s"author : ${list._1.trim}")
      println(s"updated : ${list._3.trim}")
    })
    case Failure(exception) => println(exception)
  }
}