package ir.parkban.car.adapter;

import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import ir.parkban.R;
import ir.parkban.car.model.Car;

/**
 * Created by Payam on 11/13/2017.
 */
public class CarsListAdapter extends RecyclerView.Adapter<CarsListAdapter.MyViewHolder> implements AdapterView.OnItemLongClickListener {

    private List<Car> carsList;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        carsList.get(i).getCar_id();
        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_fullname, txt_platenumber; //txt_updateDate;

        public MyViewHolder(View view) {
            super(view);
            txt_fullname = (TextView) view.findViewById(R.id.txt_carFullname);
            txt_platenumber=(TextView)view.findViewById(R.id.txt_plateNumber);
          //  txt_updateDate=(TextView)view.findViewById(R.id.txt_updateDate);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    getAdapterPosition();
                    return true;
                }
            });

        }

    }


    public CarsListAdapter(List<Car> carList) {
        this.carsList = carList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Car car = carsList.get(position);
        holder.txt_fullname.setText(car.getFullName());
        holder.txt_platenumber.setText(car.getPlateNumber());
        //holder.txt_updateDate.setText(car.getUpdateDate().toString());


    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }
}
