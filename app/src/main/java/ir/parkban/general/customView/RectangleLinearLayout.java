package ir.parkban.general.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import ir.parkban.R;


public class RectangleLinearLayout extends LinearLayout {


    float widthAndHeightRatio=-1;
    boolean ratioIsWidthBase=true;

    public RectangleLinearLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RectangleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RectangleImageview);
        widthAndHeightRatio=a.getFloat(a.getIndex(0),1);
        ratioIsWidthBase=a.getBoolean(a.getIndex(1),true);


        // TODO Auto-generated constructor stub
    }

    public RectangleLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RectangleImageview);
        widthAndHeightRatio=a.getFloat(a.getIndex(0), 1);
        ratioIsWidthBase=a.getBoolean(a.getIndex(1), false);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(widthAndHeightRatio!=-1) {
            if(ratioIsWidthBase) {
                int width = getMeasuredWidth();

                setMeasuredDimension(width, (int) (width / widthAndHeightRatio));
            }else{
                int height = getMeasuredHeight();

                setMeasuredDimension( (int) (height / widthAndHeightRatio),height);
            }
        }

    }

}
