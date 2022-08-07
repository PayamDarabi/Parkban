package ir.parkban.general.adapters;

import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.parkban.R;
import ir.parkban.general.adapters.ViewHolder.SideMenuViewHodler;

/**

 */
public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuViewHodler> {
    int[] titles = {

    //        R.string.main,
            R.string.cars,
            R.string.wallet,
            R.string.reserves,
            R.string.parkings,
            R.string.transaction,
            R.string.terms,
            R.string.settings
           /* R.string.exitو*/

    };
    int[] icons = {
      //      R.drawable.ic_location_marker,
            R.drawable.ic_directions_car_black_24dp,
            R.drawable.ic_credit_card_black_24dp,
            R.drawable.ic_announcement_black_24dp,
            R.drawable.ic_local_parking_black_24dp,
            R.drawable.ic_thumbs_up_down_black_24dp,
            R.drawable.ic_ico_info,
            R.drawable.ic_ico_settings,
         /*   R.drawable.ic_ico_exit,*/

    };


    @Override
    public SideMenuViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_side_menu, parent, false);

        return new SideMenuViewHodler(view);
    }

    @Override
    public void onBindViewHolder(SideMenuViewHodler holder, int position) {
        holder.setValue(titles[position],icons[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
