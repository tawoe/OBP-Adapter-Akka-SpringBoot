package com.openbankproject.akka.springboot.adapter.actor

import java.util.Date

import akka.actor.Actor
import com.openbankproject.akka.springboot.adapter.service.BankService
import com.openbankproject.commons.dto.{InboundAdapterInfo, InboundCheckBankAccountExists, InboundGetAccount, InboundGetBank, InboundGetBanks, InboundGetCoreBankAccounts, OutboundCheckBankAccountExists, OutboundGetAdapterInfo, OutboundGetBank, OutboundGetBanks, OutboundGetCoreBankAccounts, InboundAccount}
import javax.annotation.Resource
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component("SouthSideActor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class SouthSideActor  extends Actor  {

  @Resource
  val bankService: BankService = null

  val mockAdapaterInfo =
    s"""
       |{
       |  "name":"String",
       |  "version":"String",
       |  "git_commit":"String",
       |  "date":"${new Date()}"
       |}
    """.stripMargin

  def receive = {
    case OutboundGetBanks(callContext) => sender ! InboundGetBanks(bankService.getBanks(), callContext)
    case OutboundGetBank(bankId, callContext) => sender ! InboundGetBank(this.bankService.getBankById(bankId), callContext)
    case OutboundGetAdapterInfo(_, callContext) => sender ! InboundAdapterInfo("akka-springBoot", "01", "friday", new Date().toString, callContext)
    case OutboundCheckBankAccountExists(bankId, ccountId,callContext) => sender ! InboundCheckBankAccountExists(bankService.getAccountById(bankId,  callContext.get.userId.get,  ccountId), callContext)
    case OutboundGetCoreBankAccounts(bankIdAccountIds, callContext) => sender ! InboundGetCoreBankAccounts(bankService.getCoreBankAccounts("bankId002", callContext.get.userId.get), callContext)
  }

}
  