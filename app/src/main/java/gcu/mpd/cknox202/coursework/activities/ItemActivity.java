package gcu.mpd.cknox202.coursework.activities;
/*S1514428
Cameron Knox
Computing
*/
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import gcu.mpd.cknox202.coursework.R;
import gcu.mpd.cknox202.coursework.models.Item;

public class ItemActivity extends AppCompatActivity {

    private Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ConstraintLayout layout = findViewById(R.id.item_activity);

        layout.setBackgroundColor(getIntent().getIntExtra("backgroundColor",
                ResourcesCompat.getColor(getResources(), R.color.item_2_background, null)));

        item = getIntent().getExtras().getParcelable("item");

        displayItemData();

        FrameLayout backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayItemData() {
        TextView location = findViewById(R.id.item_location);
        location.setText(item.getLocation());

        TextView magnitude = findViewById(R.id.item_magnitude);
        magnitude.setText(String.valueOf(item.getMagnitude()));

        TextView depth = findViewById(R.id.item_depth);
        depth.setText(String.format(getResources().getString(R.string.depth_km), item.getDepth()));

        TextView category = findViewById(R.id.item_category);
        category.setText(item.getCategory());

        TextView lat = findViewById(R.id.item_latitude);
        lat.setText(String.valueOf(item.getLat()));

        TextView lon = findViewById(R.id.item_longitude);
        lon.setText(String.valueOf(item.getLon()));

        TextView originDate = findViewById(R.id.item_origin_date);
        originDate.setText(item.getOriginDateString());

        TextView pubDate = findViewById(R.id.item_pub_date);
        pubDate.setText(item.getPubDateString());

        int latitude = (int) (item.getLat()*1e6);
        int longitude = (int) (item.getLon()*1e6);





    }

}
