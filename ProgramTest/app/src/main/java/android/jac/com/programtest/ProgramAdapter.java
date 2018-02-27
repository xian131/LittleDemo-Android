package android.jac.com.programtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by jac on 2018/2/26 0026.
 */

public class ProgramAdapter extends BaseAdapter {
    private Context mContext;

    private String[] titles = null;
    private String[] urls = null;

    public ProgramAdapter(Context context) {
        mContext = context;
        titles = mContext.getResources().getStringArray(R.array.titles);
        urls = mContext.getResources().getStringArray(R.array.urls);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_main_listitem, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(titles[position]);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LiveActivity.class);
                intent.putExtra("url", urls[position]);
                intent.putExtra("title", titles[position]);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mTextView;

        public ViewHolder(View convertView) {
            mTextView = (TextView) convertView.findViewById(R.id.listItem);
        }
    }
}
