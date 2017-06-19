package com.testservice.service.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.testservice.domain.model.Transaction;
import com.testservice.manager.ServiceManager;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by justin on 02/03/2017.
 */
@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticController extends BaseRestController {

    @Autowired
    com.testservice.data.repository.StatisticRepository statisticRepository;

    @Autowired
    ServiceManager serviceManager;

    @RequestMapping(value = "/transaction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create timestamp for transaction", notes = "create new timestamp")
    public void saveVirtualAlias(
            HttpServletResponse response,
            @ApiParam(value = "amount", required = true, defaultValue = "0") @RequestParam(value = "amount", required = true) Double amount,
            @ApiParam(value = "timestamp", required = true) @RequestParam(value = "timestamp", required = true) Long timestamp
    ) {

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        LocalDateTime ldt = serviceManager.getLocalDateTimeFromEpochMilliSecond(timestamp);
        transaction.setTimestamp(ldt);
        statisticRepository.save(transaction);

        if(serviceManager.isTimeStampMoreThanOneMineOld(timestamp)){
            // return 204 if older than 60 seconds
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            // return 201 if less than 60 seconds old
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }










    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all statistics", notes = "display all statistics for the last 60 seconds")
    public Map<String,String> getAllStatistics() {
        Map<String,String> results = new HashMap<>();
        results.putAll(serviceManager.getStatsLast60Seconds());
        return results;
    }






/**
 *  this endpooint is for testing purposed...  shows all transactions
 * */
    @RequestMapping(value = "/transaction", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get all virtual aliases", notes = "display all existing virtual aliases")
    public Map<String,String> findAllVirtualAliases() {
        Map<String,String> results = new HashMap<>();
        for(Transaction t: statisticRepository.findAll()){
            results.put(t.getId().toString(), t.getAmount() + " | " + t.getTimestamp().toString());
        }
        return results;
    }


}
