package com.alodiga.app.wallet.duallibrary.listTransactionExecuted;

import com.alodiga.app.wallet.duallibrary.model.ObjTransaction;
import com.alodiga.app.wallet.duallibrary.utils.Constants;
import com.alodiga.app.wallet.duallibrary.utils.Session;
import com.alodiga.app.wallet.duallibrary.utils.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListTransactionExecutedController {

    public static SoapObject getTransactionExecuted(){

            HashMap<String, String> map = new HashMap<String, String>();

            map.put("userId", Session.getUserId());
            map.put("maxResult", Constants.MAX_RESULT_BY_TRANSACTION_OPERATION.toString());

           return  WebService.invokeGetAutoConfigString(map, Constants.WEB_SERVICES_METHOD_NAME_GET_TRANSACTION_LIST, Constants.ALODIGA);

        }

    public static ArrayList<ObjTransaction> getParseResponse(String response) {

        ArrayList<ObjTransaction> transactions = new ArrayList<ObjTransaction>();
        for (int i = 1; i < getLenghtFromResponseJson(response); i++) {

            ObjTransaction objTransaction = new ObjTransaction();
            objTransaction.setAmount(response.split("amount=")[i].split(";")[0]);
            objTransaction.setTransactionType(response.split("transactionType=")[i].split(";")[0]);
            objTransaction.setCreateionDate(response.split("creationDate=")[i].split(";")[0]);
            objTransaction.setUserDestination(response.split("destinationUser=")[i].split(";")[0]);
            objTransaction.setConcept(response.split("concept=")[i].split(";")[0]);
            objTransaction.setTransactionId(response.split("id=")[i].split(";")[0]);
            // objTransaction.setTax(response.split("totalTax=")[i].split(";")[0]);
            transactions.add(objTransaction);
        }
        return transactions;
    }


    public static int getLenghtFromResponseJson(String v) {
        return (v.split("transactions=anyType").length);
    }
}
