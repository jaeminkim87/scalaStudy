import scala.collection.mutable.{ListBuffer, Set}

val threes_multi_line = for {
  i <- 1 to 20
  if i % 3 == 0
} yield i

val two_dimension = for {
  x <- 1 to 2
  y <- 1 to 3
} {print(s"($x, $y)")}


val powersOf2 = for (i <- 0 to 8) yield {
  val pow = 1 << i
  pow
}

var x = 10
do x -= 1 while ( x > 0 )

val flag:Boolean = false
val result: Boolean = !flag

val t = Nil.::(1)

List(1,2) ::: List(2,3)

List(1,2) zip List("a","b", "c")


List(0,1,1) collect {case 1 => "OK"}

List("milk,tea") map (_.split(','))


import scala.collection.mutable.{ListBuffer, Set}
val a = List(1,2)
var b = ListBuffer(3,4)
var c = ListBuffer(3,4, 5)

val s = Set(a, b, c)
println(s)

b.append(5)

println(b)
println(s)

def checkDouble(a:String) = a.toDoubleOption
def forProduct(a:String, b: String) = {
  for {
    a1 <- checkDouble(a)
    b1 <- checkDouble(b)
  }
    yield a1 * b1
}

println(forProduct("3", "4"))