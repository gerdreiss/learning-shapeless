package com.github.learningshapeless.csv

import shapeless._

object Csv extends App {

  object CsvEncoder {
    def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc
    def instance[A](f: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
      override def encode(value: A): List[String] = f(value)
    }
  }

  def writeCsv[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
    values
      .map(enc.encode)
      .map(_.mkString(","))
      .mkString("\n")


  // manuell
  implicit val employeeEncoder: CsvEncoder[Employee] = CsvEncoder.instance { e =>
    List(
      e.name,
      e.number.toString,
      if (e.manager) "yes" else "no"
    )
  }
  //new CsvEncoder[Employee] {
  //  override def encode(value: Employee): List[String] = List(
  //    value.name,
  //    value.number.toString,
  //    if (value.manager) "yes" else "no"
  //  )
  //}
  // with shapeless
  println(the[CsvEncoder[Employee]].encode(Employee("Jacquelene", 2, manager = false)))

  case class Employee(name: String, number: Int, manager: Boolean)

  println(writeCsv(List(
    Employee("John", 1, manager = true),
    Employee("Jack", 2, manager = false)
  )))
}
