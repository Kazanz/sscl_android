package kazanski.com.sscl_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by kazanz on 4/30/16.
 */
public class WaiverAdapter extends ArrayAdapter<Waiver> {
    public WaiverAdapter(Context context, ArrayList<Waiver> waivers) {
        super(context, 0, waivers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Waiver waiver = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.waiver_list, parent, false);
        }

        TextView waiverPK = (TextView) convertView.findViewById(R.id.waiverPK);
        waiverPK.setText(String.valueOf(waiver.pk));

        if (waiver.image != "null") {
            ImageView waiverImage = (ImageView) convertView.findViewById(R.id.waiverImage);
            byte[] decodedString = Base64.decode(waiver.image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            waiverImage.setImageBitmap(decodedByte);
        }

        TextView waiverName = (TextView) convertView.findViewById(R.id.waiverName);
        waiverName.setText(waiver.name);

        if (!waiver.confirmed) {
            ImageView waiverConfirmed = (ImageView) convertView.findViewById(R.id.waiverConfirmed);
            waiverConfirmed.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
