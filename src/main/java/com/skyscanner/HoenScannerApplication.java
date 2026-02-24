package com.skyscanner;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {

    }
    @Override
    public void run(final HoenScannerConfiguration configuration,
                    final Environment environment) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // Load rental_cars.json
        List<SearchResult> rentalCars = Arrays.asList(
                mapper.readValue(
                        getClass().getClassLoader().getResource("rental_cars.json"),
                        SearchResult[].class)
        );

        // Load hotels.json
        List<SearchResult> hotels = Arrays.asList(
                mapper.readValue(
                        getClass().getClassLoader().getResource("hotels.json"),
                        SearchResult[].class)
        );

        // Combine both lists
        List<SearchResult> searchResults = new ArrayList<>();
        searchResults.addAll(rentalCars);
        searchResults.addAll(hotels);

        // Register the /search endpoint
        environment.jersey().register(new SearchResource(searchResults));
    }
    }

