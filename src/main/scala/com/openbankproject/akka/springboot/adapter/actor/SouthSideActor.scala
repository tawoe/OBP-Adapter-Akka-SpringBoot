package com.openbankproject.akka.springboot.adapter.actor

import java.util.Date

import akka.actor.Actor
import com.openbankproject.akka.springboot.adapter.service.BankService
import com.openbankproject.commons.dto._
import com.openbankproject.commons.model.{InboundAccountCommon, _}
import javax.annotation.Resource
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import scala.collection.immutable.List

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
    case OutboundGetBanks(adapterCallContext) => sender ! InboundGetBanks(bankService.getBanks(), adapterCallContext)
    case OutboundGetBank(bankId, adapterCallContext) => sender ! InboundGetBank(this.bankService.getBankById(bankId), adapterCallContext)
    case OutboundGetAdapterInfo(_, adapterCallContext) => sender ! InboundAdapterInfo("akka-springBoot", "01", "friday", new Date().toString, adapterCallContext)
    case OutboundGetBankAccountsByUsername(username, adapterCallContext) => sender ! InboundGetBankAccountsByUsername(
      List(InboundAccountCommonCommons(
        errorCode = "",
        bankId = "bankIdtob001",
        branchId = "thatBranch",
        accountId = "1",
        accountNumber = "",
        accountType = "",
        balanceAmount = "100",
        balanceCurrency = "EUR",
        owners = List("Simon"),
        viewsToGenerate = List("Owner", "Accountant", "Auditor"),
        bankRoutingScheme = "",
        bankRoutingAddress = "",
        branchRoutingScheme = "",
        branchRoutingAddress = "",
        accountRoutingScheme = "",
        accountRoutingAddress = ""
      )), adapterCallContext)
      
    case OutboundCheckBankAccountExists(bankId,accountId,adapterCallContext) => sender ! InboundCheckBankAccountExists(bankService.getAccountById(bankId,adapterCallContext.get.adapterAuthInfo.get.userId,accountId),adapterCallContext)
    case OutboundGetCoreBankAccounts(bankIdAccountIds, adapterCallContext) => sender ! InboundGetCoreBankAccounts(bankService.getCoreBankAccounts("bankId002", adapterCallContext.get.adapterAuthInfo.get.userId), adapterCallContext)
//    case OutboundGetCoreBankAccounts(bankIdAccountIds, adapterCallContext) => sender ! InboundGetCoreBankAccounts(getCoreBankAccountsAllBanks(bankIdAccountIds, adapterCallContext.get), adapterCallContext)
  }
  
//  def getCoreBankAccountsAllBanks(bankIdAccountIds: List[BankIdAccountId], adapterCallContext: AdapteradapterCallContext ) = {
//    bankIdAccountIds.flatMap( x => bankService.getCoreBankAccounts(x.bankId.toString(), adapterCallContext.get.userId.get ))
//  }

 

}
  