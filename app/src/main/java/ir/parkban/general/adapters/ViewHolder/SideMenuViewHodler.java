package ir.parkban.general.adapters.ViewHolder;

import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.parkban.R;
import ir.parkban.general.customView.CustomTextView;

/**
 * Created by k.mohammadzadeh on 8/21/2016.
 */
public class SideMenuViewHodler extends RecyclerView.ViewHolder {

    @BindView(R.id.txtTitle)
    CustomTextView txtTitle;

    @BindView(R.id.imgIcon)
    ImageView imgIcon;

    @BindView(R.id.seprator)
    View seprator;

    @BindView(R.id.linSpace)
    View linSpace;

    public SideMenuViewHodler(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public void setValue(int title,int icon){
        txtTitle.setText(title);
        if(icon!=-1) {
            imgIcon.setImageResource(icon);
            imgIcon.setVisibility(View.VISIBLE);
        }
        else{
            imgIcon.setVisibility(View.INVISIBLE);
        }

        if(getAdapterPosition()==5){
            linSpace.setVisibility(View.VISIBLE);
            seprator.setVisibility(View.VISIBLE);

        }/*else if(getAdapterPosition()==4){
            linSpace.setVisibility(View.GONE);
            seprator.setVisibility(View.VISIBLE);

        }*/else{
            linSpace.setVisibility(View.GONE);
            seprator.setVisibility(View.GONE);
        }
    }
}
