/**
 * https://docs.scala-lang.org/style/scaladoc.html
 */

println("start S08C29_Scala_Flow_Control --------------------------------------------------")

println("REPL - Read Evaluate Prints and Loop")

println("start Flow Control --------------------------------------------------")

println("Definition: ")

val word = "ZZZ"
if(word.endsWith("o")){
  println("The value of word ends with 'o'")
} else {
  println("The value of word does not end with 'o'")
}

val p = "Patrick"
if ("Patrick" == p) {
  println(s"Welcome $p!")
} else if ("Silva" == p) {
  println(s"Welcome $p!")
} else {
  println("What is your name?")
}

println("Logical Operators: AND, OR, NOT  <---->  &&, ||, !")

if ( (1==1) && (1 < 2)){
  println("(1==1) && (1 < 2) IS TRUE")
} else if ((1==1) || (1 < 2)) {
  println("(1==1) || (1 < 2) IS TRUE")
} else {
  println("None of the above options IS TRUE")
}

println("end Flow Control --------------------------------------------------")

println("end S08C29_Scala_Flow_Control --------------------------------------------------")


println("That is Flow Control in Scala")
println("Recap:")
println("")

println("Happy analysing!")

println("READ MORE ----------------------")

println("SCALA DOCUMENTATION")
println("https://docs.scala-lang.org")

println("Scala Documentation Basics")
println("https://docs.scala-lang.org/tour/basics.html")

println("Scala Language Specification")
println("http://scala-lang.org/files/archive/spec/2.12/")

println("Scala Language Specification - The Scala Standard Library")
println("http://scala-lang.org/files/archive/spec/2.12/12-the-scala-standard-library.html")

println("Scala Language - Traits")
println("https://docs.scala-lang.org/tour/traits.html")

println("JAVA DOCUMENTATION - Immutable")
println("https://docs.oracle.com/javase/tutorial/essential/concurrency/immutable.html")

println("Immutable Object - Wikipedia")
println("https://en.wikipedia.org/wiki/Immutable_object")

println("Scala Fiddle")
println("https://scalafiddle.io/")
