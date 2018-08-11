package com.github.learningshapeless.csv

import shapeless.{::, HList, HNil}

object ShapelessCsv extends App {

  def createEncoder[A](f: A => List[String]): CsvEncoder[A] =
    new CsvEncoder[A] {
      override def encode(value: A): List[String] = f(value)
    }

  implicit val stringEncoder: CsvEncoder[String] = createEncoder(s => List(s))
  implicit val intEncoder: CsvEncoder[Int] = createEncoder(i => List(i.toString))
  implicit val booleanEncoder: CsvEncoder[Boolean] = createEncoder(b => List(if (b) "yes" else "no"))
  implicit val hnilEncoder: CsvEncoder[HNil] = createEncoder(_ => Nil)

  implicit def hlistEncoder[H, T <: HList](implicit
    hEncoder: CsvEncoder[H],
    tEncoder: CsvEncoder[T]
  ): CsvEncoder[H :: T] = createEncoder {
    case h :: t => hEncoder.encode(h) ++ tEncoder.encode(t)
  }

  val reprEncoder: CsvEncoder[String :: Int :: Boolean :: HNil] = implicitly

  val encoded = reprEncoder.encode("abc" :: 123 :: true :: HNil)

  println(encoded)

}
