package gcu.mpd.cknox202.coursework.util;
/*S1514428
Cameron Knox
Computing
*/
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gcu.mpd.cknox202.coursework.R;
import gcu.mpd.cknox202.coursework.activities.ItemActivity;
import gcu.mpd.cknox202.coursework.models.Item;


public abstract class ObjectToView {

    @SuppressLint("StringFormatMatches")
    public static ConstraintLayout createSimpleItemView(
            final LayoutInflater inflater,
            final ViewGroup itemContainer,
            final Context context,
            final Item item) {

        ConstraintLayout layout = (ConstraintLayout) inflater
                .inflate(R.layout.item_layout, itemContainer, false);


        int backgroundColor = ResourcesCompat.getColor(context.getResources(), R.color.item_2_background, null);


        TextView location = (TextView) layout.getViewById(R.id.item_template_location);
        if (item.getLocation().contains(","))
            location.setText(item.getLocation().split(",")[0]);
        else
            location.setText(item.getLocation().split("\\.")[0]);


        TextView originDate = (TextView) layout.getViewById(R.id.item_template_origin_date);
        originDate.setText(item.getOriginDateString());

        TextView depth = (TextView) layout.getViewById(R.id.item_template_depth);
        depth.setText(String.format(context.getResources().getString(R.string.item_depth), item.getDepth()));

        ImageView scale = (ImageView) layout.getViewById(R.id.item_template_scale);
        double mag = item.getMagnitude();

        if (mag > 8) {
            scale.setImageResource(R.drawable.scale_over_8);
        } else if (mag >= 7.1 && mag <= 8) {
            scale.setImageResource(R.drawable.scale8);
        } else if (mag >= 6.1 && mag <= 7) {
            scale.setImageResource(R.drawable.scale7);
        } else if (mag >= 5.1 && mag <= 6) {
            scale.setImageResource(R.drawable.scale6);
        } else if (mag >= 4.1 && mag <= 5) {
            scale.setImageResource(R.drawable.scale5);
        } else if (mag >= 3.1 && mag <= 4) {
            scale.setImageResource(R.drawable.scale4);
        } else if (mag >= 2.1 && mag <= 3) {
            scale.setImageResource(R.drawable.scale3);
        } else if (mag >= 1.1 && mag <= 2) {
            scale.setImageResource(R.drawable.scale2);
        } else if (mag > 0 && mag <= 1) {
            scale.setImageResource(R.drawable.scale1);
        } else if (mag <=0){
            scale.setImageResource(R.drawable.scale_under_0);
        }

        TextView magnitude = (TextView) layout.getViewById(R.id.item_template_magnitude);
        magnitude.setText(String.format(context.getResources().getString(R.string.item_magnitude), item.getMagnitude()));


        TextView content = (TextView) layout.getViewById(R.id.display_more_content);
        content.setText(context.getResources().getString(R.string.displayContent));

        layout.setBackgroundColor(backgroundColor);

        final int finalBackgroundColor = backgroundColor;
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("backgroundColor", finalBackgroundColor);
                intent.putExtra("item", item);
                context.startActivity(intent);
            }
        });

        return layout;
    }

}
