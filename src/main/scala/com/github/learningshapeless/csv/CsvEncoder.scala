package com.github.learningshapeless.csv

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}
