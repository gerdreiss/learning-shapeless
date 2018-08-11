package com.github.learningshapeless

package object csv {

  case class IceCream(flavor: String, scoops: Int, inCone: Boolean)
  case class Employee(name: String, number: Int, manager: Boolean)
  case class IceCreamPreferences(employee: Employee, iceCream: IceCream)

  trait CsvEncoder[A] {
    def encode(value: A): List[String]
  }
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
}
