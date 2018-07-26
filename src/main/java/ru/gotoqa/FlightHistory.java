package ru.gotoqa;

/**
 * @author Muflikhunov Roman
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FlightHistory {

    public static final Logger LOGGER = LogManager.getLogger(FlightHistory.class);


    private static final String FILEPATH = "D:\\JAVA\\Java_SRC\\FlightRadar\\src\\main\\resources\\arrival.html";
    public static void main(String args[]) throws IOException, ParseException {

        File input = new File(FILEPATH);
        Document doc = Jsoup.parse(input, "utf-8");


        //#table > tbody > tr.vat
        //#table > tbody > tr > td:nth-child(4) > div:nth-child(1)
        //#table > tbody > tr > td:nth-child(2)
        //
        //#table > tbody > tr:nth-child(3) > td:nth-child(6) > div.outer > div > ul > li

        //All block tr.vat
        int x = 0;
        int y = 0;
        int z = 0;
        int x2 = 0;
        long x3 = 0;
        Elements allflightsblocks = doc.select("#table > tbody > tr.vat");
        for (Element allflightsblock : allflightsblocks) {
            String plannedTime = allflightsblock.select("td:nth-child(2)").text().replace("May  ", "May 2018 ");
            String actualTime = allflightsblock.select("td:nth-child(3)").text().replace("May  ", "May 2018 ");
            String flightNumber = allflightsblock.select("td:nth-child(4) > div:nth-child(1)").text();
            String departureAirport = allflightsblock.select("td:nth-child(5)").text();


            String status = allflightsblock.select("td:nth-child(6) > div.outer > div > ul > li").text();
            if (status.contains("Cancelled")){
                x++;
            }

            if (status.contains("Arrived")){
                y++;
            }
            //задаю формат даты
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm", Locale.ENGLISH);
            Date date = formatter.parse(plannedTime);

            Date datew = null;
            long diff = 0;
            if (!(status.contains("Cancelled"))){
                datew = formatter.parse(actualTime);
                long diffInMillies = date.getTime() - datew.getTime();
                diff = diffInMillies / (60 * 1000) % 60;
            }


            //long diffInMillies = Math.abs(date.getTime() - datew.getTime());
            //long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MINUTES);

            if (diff<0){
                x2++;
                x3 += diff;
/*                System.out.println("--------");
                System.out.println("Delay time: " +diff +" minutes");
                System.out.println();*/

                LOGGER.info("--------");
                LOGGER.info("Delay time: " +diff +" minutes");
            } else{
/*                System.out.println("--------");
                System.out.println("Upper time: " +diff +" minutes");
                System.out.println();*/

                LOGGER.info("--------");
                LOGGER.info("Upper time: " +diff +" minutes");

            }

/*            System.out.println("Planned time: " +plannedTime);
            System.out.println("Actual time: " +actualTime);
            System.out.println("Flight number: " +flightNumber);
            System.out.println("Departure airport: " +departureAirport);
            System.out.println("Status : " +status);
            System.out.println();*/

            LOGGER.info("Planned time: " +plannedTime);
            LOGGER.info("Actual time: " +actualTime);
            LOGGER.info("Flight number: " +flightNumber);
            LOGGER.info("Departure airport: " +departureAirport);
            LOGGER.info("Status : " +status);
        }

/*        System.out.println("---------------------------------");
        System.out.println("All flights: " +allflightsblocks.size());
        System.out.println("Arrived flights: " +y);
        System.out.println("Cancelled flights: " +x);
        System.out.println("Delay fly: " +x2 +" airFlights");
        System.out.println("Total delay : " +(x3 ) + " minutes");
        System.out.println("---------------------------------");*/


        LOGGER.info("---------------------------------");
        LOGGER.info("All flights: " +allflightsblocks.size());
        LOGGER.info("Arrived flights: " +y);
        LOGGER.info("Cancelled flights: " +x);
        LOGGER.info("Delay fly: " +x2 +" airFlights");
        LOGGER.info("Total delay : " +(x3 ) + " minutes");
        LOGGER.info("---------------------------------");
        LOGGER.info(LOGGER.getName());
        LOGGER.info(LOGGER.getMessageFactory());
        LOGGER.info(LOGGER.getLevel());
        LOGGER.info(LOGGER.isDebugEnabled());
    }
}
