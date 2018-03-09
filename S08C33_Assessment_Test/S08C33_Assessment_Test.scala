/*
Scala Programming Assessment Test

Create Functions to solve the followint Questions!

1.) Check for Single Even:
Write a function that takes in an integer and returns a Boolean indicating whether
or not it is even. See if you can write this in one line!
*/

def singleEven(x:Int): Boolean = {  if(x % 2 == 0){return true}else{return false}  }

println(singleEven(10))
println(singleEven(11))

/*
2.) Check for Evens in a List:
Write a function that returns True if there is an even number inside of a List,
otherwise, return False
*/
def isEvenInList(x:List[Int]): Boolean = {
  for(i <- Range(0,x.length)){
    println(s"Checking number $i: " + x(i))
    if(x(i) % 2 == 0){
      printf("%s <- even\n", x(i))
      return true
    }
  }
  return false
}

println(isEvenInList(List(1,3,5)))
println(isEvenInList(List(11,13,15,16)))

/*
3.) Lucky Number Seven:
Take in a list of integers and calculate their sum. However, sevens are lucky
and they should be counted twice, meaning their value is 14 for the sum.
Assume the list isnt' empty.
*/
def luckyNumberSeven(x:List[Int]): Int = {
  var result = 0;
  for(i <- Range(0, x.length)){
    if(x(i)==7){
      result += (x(i) * 2)
    }else{
      result += x(i)
    }
  }
  return result
}

println(luckyNumberSeven(List(1,1,7)))

/*
4.) Can you Balance?
Given a non-empty list of integers, return true if there is a place to
split the list so that the sum of numbers on one side
is equal to the sum of the numbers on the other side. For example, given the
list (1,5,3,3) would return true, you can split it in the middle. Another
example (7,3,4) would return true 3+4=7. Remember you just need to return the
boolean, not the split index point.
*/
def isBalanceable(x:List[Int]): Boolean = {
  for(i <- Range(0,x.length)){
    if(x.slice(0,i).sum == x.slice(i,x.length).sum){
      return true
    }
  }
  return false
}

println("isBalanceable(List(2,4,6))?")
println(isBalanceable(List(2,4,6)))
println("isBalanceable(List(1,1,1,1,4))?")
println(isBalanceable(List(1,1,1,1,4)))
println("isBalanceable(List(6,3,1,1,1))?")
println(isBalanceable(List(6,3,1,1,1)))
println("isBalanceable(List(16,3,1,1,1))?")
println(isBalanceable(List(16,3,1,1,1)))

/*
5.) Palindrome Check
Given a String, return a boolean indicating wheter or not it is a Palindrome.
(Spelled the same forwards and backwards). Try exploring methods to help you.
*/
def isPalindrome(str:String): Boolean = {
  if(str == str.reverse){
    return true
  }
  return false
}

println("isPalindrome(\"ana1\")")
println(isPalindrome("ana1"))
println("isPalindrome(\"ana\")")
println(isPalindrome("ana"))
