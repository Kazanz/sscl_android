package kazanski.com.sscl_app;

/**
 * Created by kazanz on 4/30/16.
 */
public class Waiver {
    public int pk;
    public String image;
    public String name;
    public boolean confirmed = false;

    public Waiver(int pk, String name, boolean confirmed, String image) {
        this.pk = pk;
        this.name = name;
        this.confirmed = confirmed;
        this.image = image;
    }
}
