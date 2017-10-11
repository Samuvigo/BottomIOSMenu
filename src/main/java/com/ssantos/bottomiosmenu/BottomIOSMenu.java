package com.ssantos.bottomiosmenu;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssantos.bottomiosmenu.apdater.BottomIOSMenuAdapter;
import com.ssantos.bottomiosmenu.model.OptionMenu;

import java.util.List;

/**
 * Created by ssantos on 10/10/2017.
 */
public class BottomIOSMenu extends Dialog implements View.OnClickListener {

    private Context context;
    static BottomIOSMenu instance;
    List<OptionMenu> menu;
    String title;
    String buttonText;
    BottomMenuItemClickListener listener;
    RelativeLayout rl;
    Button bottomButton;
    int itemstoshow = -1;

    private BottomIOSMenu(Context context, List<OptionMenu> menu, String title, String button) {
        super(context, android.R.style.Theme_Light);
        this.context = context;
        this.menu = orderMenu(menu);
        this.title = title;
        this.buttonText = button;
    }

    private List<OptionMenu> orderMenu(List<OptionMenu> menu) {
        int n = menu.size();
        OptionMenu temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (menu.get(j - 1).getPosition() > menu.get(j).getPosition()) {
                    temp = menu.get(j - 1);
                    menu.set((j - 1), menu.get(j));
                    menu.set(j, temp);
                }
            }
        }
        return menu;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bottom_dialog);
        rl = findViewById(R.id.dialog_anim);
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slide_up_dialog);
        ListView list = findViewById(R.id.list_item_menu);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        BottomIOSMenuAdapter adapter = new BottomIOSMenuAdapter(context, menu);
        if (itemstoshow > -1) {
            if (adapter.getCount() > itemstoshow) {
                View item = adapter.getView(0, null, list);
                item.measure(0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (itemstoshow * item.getMeasuredHeight()));
                list.setLayoutParams(params);
                rl.requestLayout();
                findViewById(R.id.view_onedp).requestLayout();
                tvTitle.requestLayout();

            }
        }
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onBottomMenuItemClickListener(menu.get(i));
            }
        });

        bottomButton = findViewById(R.id.bt_bottom);
        bottomButton.setText(buttonText);
        bottomButton.setOnClickListener(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rl.startAnimation(slide);

    }

    public void setOnBottomMenuItemClickListener(BottomMenuItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }

    @Override
    public void dismiss() {
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down_dialog);
        rl.startAnimation(slideDown);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                bottomButton.setOnClickListener(null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                BottomIOSMenu.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void setItemtoShow(int itemtoShow) {
        this.itemstoshow = itemtoShow;
    }

    public interface BottomMenuItemClickListener {
        void onBottomMenuItemClickListener(OptionMenu option);
    }

    public static class Builder {

        BottomIOSMenu instance;

        public Builder(Context context, List<OptionMenu> menu, String title, String button) {
            instance = new BottomIOSMenu(context, menu, title, button);
        }


        public Builder setOnBottomMenuItemClickListener(BottomMenuItemClickListener listener) {
            instance.setOnBottomMenuItemClickListener(listener);
            return this;
        }

        public BottomIOSMenu build() {
            return instance;
        }

        public Builder setItemtoShow(int itemtoShow) {
            instance.setItemtoShow(itemtoShow);
            return this;
        }


    }

}
