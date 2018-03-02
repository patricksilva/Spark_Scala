/*
Scala Programming Assessment Test

Create Functions to solve the followint Questions!

1.) Check for Single Even:
Write a function that takes in an integer and returns a Boolean indicatin whether
or not it is even. See if you can write this in one line!
*/

def singleEven(x:Int): Boolean = {  if(x % 2 == 0){return true}else{return false}  }
println(singleEven(10))
println(singleEven(11))

/*
def singleEven(x:List[Int]): List[Boolean] = { ... }
for(n <- Range(2,numcheck)){
  if(numck % n == 0){
    return false
  }
}
*/
