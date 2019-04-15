package com.openbankproject.akka.springboot.adapter

import com.openbankproject.akka.springboot.adapter.service.BankService
import javax.annotation.Resource
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(classOf[SpringRunner])
@SpringBootTest
class BankServiceTests {

  @Resource
  val bankService: BankService = null

  @Test
  def getBanksTest = {
    val accounts = this.bankService.getCoreBankAccounts("bankId", "userId")
    println(accounts)
  }
}
