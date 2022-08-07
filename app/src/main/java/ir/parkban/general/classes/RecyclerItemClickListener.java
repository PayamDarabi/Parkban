package ir.parkban.general.classes;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Novin Pendar on 11/22/2015.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    @Nullable
    private View childView;

    private int childViewPosition;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongPress(View v, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (childView != null) {
                    mListener.onItemLongPress(childView, childViewPosition);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        childView = view.findChildViewUnder(e.getX(), e.getY());
        childViewPosition = view.getChildAdapterPosition(childView);
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;

    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
