package habitatfinal.habitatfinal;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 15-04-12.
 */
public class UserData extends Application {
    private String email, firstName, lastName;
    private ArrayList<Car> cars;

    public UserData(){

        email = "";
        firstName = "";
        lastName = "";
        cars = new ArrayList<Car>();

    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public ArrayList<Car> getCars(){
        return cars;
    }

    public void setEmail(String str) {
        Log.d("setting email", str);
        email = str;
    }
    public void setFirstName(String str) {
        Log.d("setting email", str);
        firstName = str;
    }
    public void setLastName(String str) {
        Log.d("setting email", str);
        lastName = str;
    }
}
