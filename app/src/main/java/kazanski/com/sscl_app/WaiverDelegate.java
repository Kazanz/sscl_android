package kazanski.com.sscl_app;

import java.io.IOException;
import java.util.ArrayList;

import kazanski.com.sscl_app.Waiver;

/**
 * Created by kazanz on 5/1/16.
 */
public interface WaiverDelegate {
    public void asyncComplete(ArrayList<Waiver> waivers) throws IOException;
}
