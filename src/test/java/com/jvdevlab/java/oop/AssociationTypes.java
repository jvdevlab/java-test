package com.jvdevlab.java.oop;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AssociationTypes {

    @Test
    public void association() {
        class Road {
        }

        class Car {
            boolean driverOn(Road road) {
                return true;
            }
        }

        class City {
            boolean trip(Car car, Road road) {
                return car.driverOn(road);
            }
        }

        City oceanCity = new City();
        Car honda = new Car();
        Road sandRoad = new Road();

        // the car and the city are associated
        assertNotNull(oceanCity.trip(honda, sandRoad));
    }

    @Test
    public void aggregation() {
        class Car {
        }

        class Driver {
            private Car car;

            Driver(Car car) {
                this.car = car;
            }
        }

        // Car doesn't have to have a driver (autopilot)
        Car honda = new Car();
        // Driver must have a car by definition.
        // He HAS-A car.
        Driver john = new Driver(honda);

        assertNotNull(john.car);
    }

    @Test
    public void composition() {
        class Engine {
        }

        class Car {
            // PART-OF
            Engine engine = new Engine();
        }

        Car honda = new Car();

        // they are mutually dependent
        // when car dies engine dies as well
        // when engine dies car dies as well
        assertNotNull(honda.engine);
    }
}
