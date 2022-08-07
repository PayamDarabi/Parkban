package ir.parkban.car.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import ir.parkban.R;
import ir.parkban.car.adapter.CarsListAdapter;
import ir.parkban.car.model.Car;
import ir.parkban.general.activities.BaseActivity;
import ir.parkban.general.storage.PreferenceManager;
import ir.parkban.general.utils.Constants;

public class CarsActivity extends BaseActivity {

    @BindView(R.id.rw_cars)
    RecyclerView rw_cars;

    @BindView(R.id.fab_add_Car)
    FloatingActionButton fab_add_Car;

    private List<Car> carsList = new ArrayList<>();
    private CarsListAdapter mAdapter;

    private static String user_id;
    private static int selected_colorId;
    private static int selected_typeId;
    private static int selected_charId;
    static HashMap<Integer, String> colorMap;
    static HashMap<Integer, String> typeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);
        setTitle(R.string.cars);
        setActionButtonVisibility(View.GONE, View.GONE);
        setHashMapsData();
        final String userName = PreferenceManager.getInstance(this).getUsername();

        // Log the result to output
        fillCarsListData(userName);

        fab_add_Car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddCarActivity.createIntent(CarsActivity.this));
            }
        });
    }

    private void fillCarsListData(String user_id) {
       // try {
           /* BacktoryQuery todoQuery = new BacktoryQuery("Cars");
            todoQuery.whereEqualTo("user_id", user_id);
            todoQuery.findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                @Override
                public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                    if (backtoryResponse.isSuccessful()) {
                        List<BacktoryObject> mycars = backtoryResponse.body();
                        Car[] carList = new Car[mycars.size()];

                        for (int i = 0; i < mycars.size(); i++) {
                            carList[i] = new Car();
                            carList[i].setCar_id(mycars.get(i).getObjectId());
                            carList[i].setCarColor(colorMap.get(Integer.valueOf(mycars.get(i).getString("CarColor"))));
                            carList[i].setCarType(typeMap.get(Integer.valueOf(mycars.get(i).getString("CarType"))));
                            carList[i].setCreateDate(mycars.get(i).getCreatedAt().toString());
                            carList[i].setUpdateDate(mycars.get(i).getUpdatedAt().toString());
                            carList[i].setFullName(mycars.get(i).getString("FullName"));
                            carList[i].setPlateNumber(mycars.get(i).getString("PlateNumber"));
                            carsList.add(carList[i]);
                        }

                        mAdapter = new CarsListAdapter(carsList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rw_cars.setLayoutManager(mLayoutManager);
                        rw_cars.setItemAnimator(new DefaultItemAnimator());
                        rw_cars.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                        rw_cars.setAdapter(mAdapter);

                        rw_cars.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                                rw_cars, new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                            }

                            @Override
                            public void onLongClick(View view, int position) {
                                final Car car = carsList.get(position);
                                Toast.makeText(getApplicationContext(), car.getCar_id() + " is selected!", Toast.LENGTH_SHORT).show();

                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //Yes button clicked
                                                BacktoryQuery carQuery = new BacktoryQuery("Cars");
                                                carQuery.getInBackground(car.getCar_id(),
                                                        new BacktoryCallBack<BacktoryObject>() {
                                                            @Override
                                                            public void onResponse(BacktoryResponse<BacktoryObject> response) {
                                                                if (response.isSuccessful()) {
                                                                    BacktoryObject car = response.body();
                                                                    car.deleteInBackground(new BacktoryCallBack<Void>() {
                                                                        @Override
                                                                        public void onResponse(BacktoryResponse<Void> backtoryResponse) {
                                                                            Toast.makeText(CarsActivity.this, "خودرو مورد نظر با موفقیت حذف شد",
                                                                                    Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                    // get note data by calling get functions on received BacktoryObject
                                                                    // for example, titleTextView.setText(note.getString("title")) 
                                                                } else {
                                                                }
                                                            }
                                                        });

                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                Intent intent = new Intent(getBaseContext(), EditCarActivity.class);
                                                intent.putExtra("car_id", car.getCar_id());
                                                startActivity(intent);
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setMessage("حذف/ ویرایش خودرو").setPositiveButton("حذف", dialogClickListener)
                                        .setNegativeButton("ویرایش", dialogClickListener).show();


                            }
                        }));


                    } else {
                        // see response.message() to know the cause of error 
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
     //   }
    }

    private void setHashMapsData() {

        colorMap = Constants.colorMap;
        String[] colorArray = new String[colorMap.size()];

        for (int i = 0; i < colorMap.size(); i++)
            colorArray[i] = colorMap.get(i).toString();




        typeMap = Constants.typeMap;
        String[] typeArray = new String[typeMap.size()];

        for (int i = 0; i < typeMap.size(); i++)
            typeArray[i] = typeMap.get(i).toString();

    }

    public static Intent createIntent(Context context) {
        return new Intent(context, CarsActivity.class);
    }


}
