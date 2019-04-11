@Grab("groovy-json")
import groovy.json.JsonSlurper

@RestController
class RemoteEndpointApi {

    @GetMapping(value="/banks")
    def getBanks() {
        parseJson(
         """[
            {
              "bankId":"bankIdtob001",
              "shortName":"The Royal Bank of Scotland",
              "fullName":"The Royal Bank of Scotland",
              "logoUrl":"http://www.red-bank-shoreditch.com/logo.gif",
              "websiteUrl":"http://www.red-bank-shoreditch.com",
              "bankRoutingScheme":"OBP",
              "bankRoutingAddress":"rbs"
            },
             {
              "bankId":"bankId002",
              "shortName":"The Royal Bank of Scotland",
              "fullName":"The Royal Bank of Scotland",
              "logoUrl":"http://www.red-bank-shoreditch.com/logo.gif",
              "websiteUrl":"http://www.red-bank-shoreditch.com",
              "bankRoutingScheme":"OBP",
              "bankRoutingAddress":"rbs"
            }
            ]
        """
        )
    }

    @GetMapping(value="/banks/{BANK_ID}")
    def getBankById(@PathVariable("BANK_ID") bankId) {
        parseJson(
            """
            {
              "bankId":"${bankId}",
              "shortName":"The Royal Bank of Scotland",
              "fullName":"The Royal Bank of Scotland",
              "logoUrl":"http://www.red-bank-shoreditch.com/logo.gif",
              "websiteUrl":"http://www.red-bank-shoreditch.com",
              "bankRoutingScheme":"OBP",
              "bankRoutingAddress":"rbs"
            }
            """
        )
    }
    @GetMapping(value="/banks/{BANK_ID}/accounts")
    def getAllAccountsByBankId(@PathVariable("BANK_ID") bankId) {
        parseJson(
         """{
            "accounts":[
                {
                    "id":"12345678",
                    "label":"NoneLabel",
                    "bank_id":"${bankId}",
                    "number":"12345678"
                }
                ]    
            }
        """
        )
    }

    @GetMapping(value="/banks/{BANK_ID}/{USER_ID}/{ACCOUNT_ID}")
    def getAccountById(@PathVariable("BANK_ID") bankId, @PathVariable("USER_ID") userId, @PathVariable("ACCOUNT_ID") accountId) {
        parseJson(
                """{
     "bankId" : "${bankId}",
     "branchId" : "thatBranch",
     "accountId" : "${accountId}",
     "accountNumber" : "${accountId}",
     "accountType" : "mockedAccount",
     "balanceAmount" : "EUR",
     "balanceCurrency" : "2333",
     "owners : [ "$userId" ],
     "viewsToGenerate : ["Owner", "Accountant", "Auditor"],
     "bankRoutingScheme" :  "OBP",
     "bankRoutingAddress" : "rbs",
     "branchRoutingScheme" : "none",
     "branchRoutingAddress" :  "none",
     "accountRoutingScheme" : "obsolete",
     "accountRoutingAddress" : "obsolete",
     "accountRouting" : [
        {"scheme": "OBP",
        "address": "whatever"}
        ]
     }"""
        )
    }

    @GetMapping(value="/banks/{BANK_ID}/{USER_ID}")
    def getCoreBankAccounts(@PathVariable("BANK_ID") bankId, @PathVariable("USER_ID") userId) {
        parseJson(
                """{"accounts": 
        [
        {
            "id": "${userId}--1234511",
            "label": "some label",
            "bank_id": "${bankId}",
            "account_type": "mockedAccount",
            "account_routings": [
                {
                    "scheme": "OBP",
                    "address": "whatever"
                }
            ]
        },
        {
            "id": "${userId}--1234512",
            "label": "another label",
            "bank_id": "${bankId}",
            "account_type": "mockedAccount",
            "account_routings": [
                {
                    "scheme": "OBP",
                    "address": "whatever"
                }
            ]
        },
        {
            "id": "${userId}--1234513",
            "label": "EPARG",
            "bank_id": "${bankId}",
            "account_type": "mockedAccount",
            "account_routings": [
                {
                    "scheme": "OBP",
                    "address": "whatever"
                }
            ] 
         } 
         ] 
     }"""
        )
    }
    
    

    def parseJson(jsonStr) {
        new JsonSlurper().parseText(jsonStr)
    }
}