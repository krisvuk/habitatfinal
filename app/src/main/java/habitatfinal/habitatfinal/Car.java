package habitatfinal.habitatfinal;

/**
 * Created by sandeep on 2015-03-15.
 */
public class Car {
    private int carId;
    private String carType;

    public Car(int c, String ct)
    {
        this.carId = c;
        this.carType = ct;
    }
    public int getCarId() {
        return carId;
    }

    public String getCarType() {
        return carType;
    }
}
