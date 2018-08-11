package com.github.learningshapeless.csv


object RegularCsv extends App {

  implicit val employeeEncoder: CsvEncoder[Employee] = CsvEncoder.instance { e =>
    List(
      e.name,
      e.number.toString,
      if (e.manager) "yes" else "no"
    )
  }

  println(writeCsv(List(
    Employee("John", 1, manager = true),
    Employee("Jack", 2, manager = false)
  )))

  // can't encode IceCream, need iceCreamEncoder
  //println(writeCsv(List(
  //  IceCream("vanille", 3, inCone = true),
  //  IceCream("choco", 3, inCone = false)
  //)))
}
