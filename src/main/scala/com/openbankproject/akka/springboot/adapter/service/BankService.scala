package com.openbankproject.akka.springboot.adapter.service

import com.openbankproject.akka.springboot.adapter.entity.BankAccount
import com.openbankproject.commons.dto.{InboundAccount, InboundBank, InboundCoreAccount}
import com.openbankproject.commons.model.BankIdAccountId
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.{GetMapping, PathVariable}

import scala.tools.nsc.interpreter.JList

@FeignClient(name="account", url="${adapter.remote.base.url}")
trait BankService {

  @GetMapping(Array("banks/{BANK_ID}"))
  def getBankById(@PathVariable("BANK_ID") bankId: String): InboundBank


  @GetMapping(Array("/banks"))
  def getBanks(): List[InboundBank]


  @GetMapping(Array("/banks/{BANK_ID}/accounts"))
  def getAccounts(@PathVariable("BANK_ID") bankId :String): AccountResult
  
  @GetMapping(Array("/banks/{BANK_ID}/{USER_ID}/{ACCOUNT_ID}"))
  def getAccountById(@PathVariable("BANK_ID") bankId: String, @PathVariable("USER_ID") userId: String , @PathVariable("ACCOUNT_ID") accountId: String): InboundAccount

  @GetMapping(Array("/banks/{BANK_ID}/{USER_ID}"))
  def getCoreBankAccounts(@PathVariable("BANK_ID") bankId: String, @PathVariable("USER_ID") userId: String): List[InboundCoreAccount]
  
}

case class AccountResult(var accounts: JList[BankAccount])