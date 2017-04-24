package com.example.ilzxm.peppapig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import utils.SysApplication;

public class LevelActivity extends Activity {
    private GridView gridView;
    private Bundle bundle1;
    private static boolean isExit = false;
    private int judge=0;
    private int star_judge=0;
    private int level_num;
    private int star_num;
    //图片的文字标题
    private int[] titles = new int[]{
            R.mipmap.star_yellow_3, R.mipmap.star_yellow_2, R.mipmap.star_yellow_2,
            R.mipmap.star_yellow_1, R.mipmap.star_yellow_1, R.mipmap.star_yellow_0,
            R.mipmap.star_yellow_0, R.mipmap.star_yellow_0, R.mipmap.star_yellow_0
    };
    //图片ID数组
    private int[] images = new int[]{
            R.mipmap.pic_1, R.mipmap.pic_2, R.mipmap.pic_3,
            R.mipmap.pic_4, R.mipmap.pic_5, R.mipmap.pic_6,
            R.mipmap.pic_7, R.mipmap.pic_8, R.mipmap.pic_9
    };
private void count_star(int i,int j)
{
    switch(j){
        case 0:
            titles[i]=R.mipmap.star_yellow_0;
            break;
        case 1:
            titles[i]=R.mipmap.star_yellow_1;
            break;
        case 2:
            titles[i]=R.mipmap.star_yellow_2;
            break;
        case 3:
            titles[i]=R.mipmap.star_yellow_3;
            break;
    }
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.activity_level);
        bundle1=this.getIntent().getExtras();
        star_judge= bundle1.getInt("star_judge");
        if(star_judge==1)
        {
            level_num= bundle1.getInt("level_num")-1;
            star_num= bundle1.getInt("star_num");
            count_star(level_num,star_num);
        }
        gridView = (GridView) findViewById(R.id.gridview);
        PictureAdapter adapter = new PictureAdapter(titles, images, this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(LevelActivity.this, PlayModeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("level_num", position+1);
                bundle.putInt("judge", judge);
                intent.putExtras(bundle);
                SysApplication.getInstance().addActivity(LevelActivity.this);
                LevelActivity.this.startActivity(intent);
//                Toast.makeText(LevelActivity.this, "pic" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            SysApplication.getInstance().exit();
        }
    }

    //自定义适配器
    class PictureAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<Picture> pictures;

        public PictureAdapter(int[] titles, int[] images, Context context) {
            super();
            pictures = new ArrayList<Picture>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < images.length; i++) {
                Picture picture = new Picture(titles[i], images[i]);
                pictures.add(picture);
            }
        }

        @Override
        public int getCount() {
            if (null != pictures) {
                return pictures.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return pictures.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.picture_item, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (ImageView) convertView.findViewById(R.id.title);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setImageResource(pictures.get(position).getTitle());
            viewHolder.image.setImageResource(pictures.get(position).getImageId());
            return convertView;
        }

    }

    class ViewHolder {
        public ImageView title;
        public ImageView image;
    }

    class Picture {
        private int title;
        private int imageId;

        public Picture() {
            super();
        }

        public Picture(int title, int imageId) {
            super();
            this.title = title;
            this.imageId = imageId;
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

    }
}