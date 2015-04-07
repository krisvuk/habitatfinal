package habitatfinal.habitatfinal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeep on 2015-04-06.
 */
public class User implements Serializable{
    private String email, pwd, first_name, last_name, country;
    public User(ArrayList<String> user)
    {
        email = user.get(0);
        pwd = user.get(1);
        first_name = user.get(2);
        last_name = user.get(3);
        country = user.get(4);
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPwd() {
        return pwd;
    }
}
