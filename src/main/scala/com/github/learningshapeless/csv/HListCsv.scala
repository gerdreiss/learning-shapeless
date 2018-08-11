package com.github.learningshapeless.csv

import shapeless._

object HListCsv extends App {

  implicit val stringEncoder : CsvEncoder[String]  = CsvEncoder.instance(s => List(s))
  implicit val intEncoder    : CsvEncoder[Int]     = CsvEncoder.instance(i => List(i.toString))
  implicit val booleanEncoder: CsvEncoder[Boolean] = CsvEncoder.instance(b => List(if (b) "yes" else "no"))
  implicit val hnilEncoder   : CsvEncoder[HNil]    = CsvEncoder.instance(_ => Nil)

  implicit def hlistEncoder[H, T <: HList](implicit
    hEncoder: CsvEncoder[H],
    tEncoder: CsvEncoder[T]
  ): CsvEncoder[H :: T] = CsvEncoder.instance {
    case h :: t => hEncoder.encode(h) ++ tEncoder.encode(t)
  }

  implicit def genericEncoder[A, R](implicit
    gen: Generic.Aux[A, R],
    enc: CsvEncoder[R]
  ): CsvEncoder[A] = CsvEncoder.instance(a => enc.encode(gen.to(a)))

  val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly

  println(reprEncoder.encode("abc" :: 123 :: true :: HNil))

  // this is not necessary, since we have a generic encoder above
  //implicit val employeeEncoder: CsvEncoder[Employee] = {
  //  val gen = Generic[Employee]
  //  val enc = CsvEncoder[gen.Repr]
  //  CsvEncoder.instance(employee => enc.encode(gen.to(employee)))
  //}


  println(writeCsv(List(
    Employee("John", 1, manager = true),
    Employee("Jack", 2, manager = false)
  )))
  println(writeCsv(List(
    IceCream("vanille", 3, inCone = true),
    IceCream("choco", 3, inCone = false)
  )))
  // TODO make this work
  //println(writeCsv(List(
  //  IceCreamPreferences(
  //    Employee("John", 1, manager = true),
  //    IceCream("vanille", 3, inCone = true)
  //  )
  //)))

}
