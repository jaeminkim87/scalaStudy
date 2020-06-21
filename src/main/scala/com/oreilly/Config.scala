package com.oreilly {
  private[oreilly] class Config {
    val url = "http://localhsot"
  }

  class Authentication {
    private[oreilly] val password = "jason"
    def validate = password.nonEmpty
  }

  class Test {
    println(s"url = ${new Config().url}")
    println(s"password = ${new Authentication().password}")
  }
}

